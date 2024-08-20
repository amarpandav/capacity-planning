package com.ubs.cpt.infra.query;

import com.ubs.cpt.domain.EntityId;
import com.ubs.cpt.domain.entity.user.UserKey;
import com.ubs.cpt.infra.datetime.DateTimeService;
import com.ubs.cpt.infra.util.CollectionUtils;

import java.math.BigInteger;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

/**
 * Use this as a wrapper around Object arrays when transforming query results into DTOs.
 *
 * @author Amar Pandav
 */
public class TransformationSource {
    private final DateTimeService dateTimeService;

    // It might well be that this is premature optimisation and it would be better to create value objects every time
    private List<Value> cachedValueList;

    private Object[] values;

    private int currentIndex = 1;

    public TransformationSource(DateTimeService dateTimeService) {
        this.dateTimeService = dateTimeService;
    }

    // ------------------------------------------ Public methods

    public void resetInput(Object... values) {
        // Populate this list only once - no matter how many rows we're going to process with this transformation source
        if (cachedValueList == null) {
            cachedValueList = new ArrayList<>(values.length);
            for (int i = 0; i < values.length; ++i) {
                cachedValueList.add(new Value(i));
            }
        }

        // .. just make sure that the developer who uses this class doesn't reuse the same transformation source too often.
        if (cachedValueList.size() != values.length) {
            throw new IllegalStateException("Lengths of the cached value list and the given value array differ. " +
                    "Please don't use this transformation source instance across different transformers, etc. " +
                    "You might run into race conditions otherwise.");
        }

        this.currentIndex = -1;
        this.values = values;
    }

    /**
     * Allows you to iterate through the underlying array you gave.
     */
    public Value next() {
        return get(++currentIndex);
    }

    public Value current() {
        return get(currentIndex);
    }

    /**
     * Allows you to index directly into the underlying array you gave.
     */
    public Value get(int index) {
        if (index < 0 || index > cachedValueList.size()) {
            throw new IndexOutOfBoundsException(
                    "Values of the current row: '" + Arrays.toString(values) + "' and your index: " + index);
        }

        return cachedValueList.get(index);
    }

    /**
     * Allows you to index directly into the underlying array you gave, just in a more readable way, if you define an
     * enum for your result set. The ordinal of the enum will be taken as the actual index.
     */
    public Value get(Enum<?> enumIndex) {
        return get(enumIndex.ordinal());
    }

    // ------------------------------------------ Utility methods

    /**
     * Converts a transformer based on such a transformation source to one that only requires raw Object arrays.
     */
    public static <T> CollectionUtils.Transformer<Object[], T> with(
            final CollectionUtils.Transformer<TransformationSource, T> delegate, DateTimeService dateTimeService) {
        final TransformationSource transformationSource = new TransformationSource(dateTimeService);
        return new CollectionUtils.Transformer<Object[], T>() {
            @Override
            public T transform(Object[] input) {
                transformationSource.resetInput(input);
                return delegate.transform(transformationSource);
            }
        };
    }

    /**
     * Converts a transformer function based on such a transformation source to one that only requires raw Object arrays.
     */
    public static <T> Function<Object[], T> with(final Function<TransformationSource, T> delegate, DateTimeService dateTimeService) {
        final TransformationSource transformationSource = new TransformationSource(dateTimeService);
        return new Function<Object[], T>() {
            @Override
            public T apply(Object[] input) {
                transformationSource.resetInput(input);
                return delegate.apply(transformationSource);
            }
        };
    }

    // ------------------------------------------ Utility classes

    /**
     * Wrapper around a value returned from a transformation source - for validations and conversions.
     * VERY IMPORTANT: Don't keep references to these things! Their values might change later on, if someone resets
     * the input. This is just a convenience thing you should use to validate and convert values. It also produces
     * exceptions that are much more readable, give plenty of information, etc!
     */
    public class Value {

        /**
         * The column for which we've read the value.
         */
        private final int index;

        // -------------------------------------- Constructors

        protected Value(int index) {
            this.index = index;
        }

        // -------------------------------------- Public methods

        /**
         * Performs a not-null check on the value that we extracted from the input source. If an error
         * occurs it will throw an exception with information about the data we got.
         */
        public Value notNull() {
            if (asObject() == null) {
                throw new NullPointerException("The value for the column '" + index + "' is null even " +
                        "though it really shouldn't be. The remaining values " +
                        "of that row were '" + Arrays.toString(values) + "'.");
            }

            return this;
        }

        public Object asObject() {
            return values[index];
        }

        public String asString() {
            try {
                return (String) asObject();
            } catch (ClassCastException ex) {
                throw newConversionException(ex);
            }
        }

        public Long asLong() {
            try {
                if (asObject() instanceof BigInteger) {
                    return asNumber().longValue();
                } else if (asObject() instanceof Long) {
                    return (Long) asObject();
                }
                return null;
            } catch (ClassCastException ex) {
                throw newConversionException(ex);
            }
        }

        public Boolean asBoolean() {
            try {
                if (asObject() instanceof Boolean) {
                    return (Boolean) asObject();
                }

                Number booleanAsNumber = asNumber();
                if (booleanAsNumber != null) {
                    return booleanAsNumber.intValue() == 1;
                }
                return null;
            } catch (ClassCastException ex) {
                throw newConversionException(ex);
            }
        }

        public boolean asBooleanPrimitive() {
            try {
                return Boolean.TRUE.equals(this.asBoolean());
            } catch (ClassCastException ex) {
                throw newConversionException(ex);
            }
        }

        public Double asDouble() {
            try {
                if (asObject() instanceof BigInteger) {
                    return asNumber().doubleValue();
                } else if (asObject() instanceof Double) {
                    return (Double) asObject();
                }
                return null;
            } catch (ClassCastException ex) {
                throw newConversionException(ex);
            }
        }

        public double asDoublePrimitive() {
            try {
                return asDouble() == null ? 0 : asDouble();
            } catch (ClassCastException ex) {
                throw newConversionException(ex);
            }
        }

        public Number asNumber() {
            try {
                return (Number) asObject();
            } catch (ClassCastException ex) {
                throw newConversionException(ex);
            }
        }

        public LocalDateTime asDateTime() {
            try {
                return switch (asObject()) {
                    case Number timestamp -> LocalDateTime.ofInstant(Instant.ofEpochMilli(timestamp.longValue()), dateTimeService.zoneOffset());
                    case null -> null;
                    default -> throw new IllegalArgumentException("Cannot convert " + asObject() + " to LocalDateTime");
                };
            } catch (IllegalArgumentException ex) {
                throw newConversionException(ex);
            }
        }

        public LocalDate asLocalDate() {
            try {
                return switch (asObject()) {
                    case Number timestamp -> LocalDate.ofInstant(Instant.ofEpochMilli(timestamp.longValue()), dateTimeService.zoneOffset());
                    case null -> null;
                    default -> throw new IllegalArgumentException("Cannot convert " + asObject() + " to LocalDateTime");
                };
            } catch (IllegalArgumentException ex) {
                throw newConversionException(ex);
            }
        }

        // Please don't use a method like this as it suggests you're using JPA queries rather than native
        // queries. There's nothing wrong with that, but at least keep the transformer you're writing independent
        // of that fact. I've changed the accessor methods for these embeddable classes so that you can use them
        // for both native and JPA queries now. There's a few instanceof checks which wouldn't be necessary otherwise.
        // In the unlikely case that this turns out to be a performance problem, introduce a different with() method
        // that works only with native and one that only works with JPA queries. Otherwise the transformer callback,
        // however, stays the same!
//		public <T> T asTypedObject() {
//			Object o = asObject();
//			return (T) o;
//		}

        @SuppressWarnings("unchecked")
        public <T> EntityId<T> asEntityId() {
            if (asObject() instanceof EntityId) {
                return (EntityId<T>) asObject();
            }

            String uuid = asString();
            return uuid != null ? EntityId.<T>fromUuid(uuid) : null;
        }

        public  UserKey asUserKey() {
            if (asObject() instanceof UserKey) {
                return (UserKey) asObject();
            }

            String key = asString();
            return key != null ? new UserKey(key) : null;
        }


        public <T extends Enum<T>> T asEnum(Class<T> enumType) {
            if (asObject() instanceof Enum) {
                try {
                    return enumType.cast(asObject());
                } catch (ClassCastException ex) {
                    throw newConversionException(ex);
                }
            }

            String string = asString();
            if (string == null) {
                return null;
            }

            try {
                return Enum.valueOf(enumType, string.trim());
            } catch (IllegalArgumentException ex) {
                throw newConversionException(ex);
            }
        }

        private IllegalStateException newConversionException(Exception ex) {
            return new IllegalStateException("The current value '" + asObject() + "' could not be " +
                    "converted as you wanted. The remaining values of the row were '" + Arrays.toString(values) + "'.", ex);
        }

    }

}
