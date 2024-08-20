package com.ubs.cpt.testdata.availability;

import com.ubs.cpt.domain.entity.availability.Availability;
import com.ubs.cpt.domain.entity.availability.AvailabilityType;
import com.ubs.cpt.infra.test.base.TestDataSuite;

/**
 * Testdata for {@link Availability}.
 *
 * @author Amar Pandav
 */
public class AvailabilityTestdata {

   private AvailabilityType availabilityType;

    private String availabilityDescription;

    private String availabilityTypeStyleClass;


    public AvailabilityTestdata withAvailabilityType(AvailabilityType availabilityType) {
        this.availabilityType = availabilityType;
        return this;
    }

    public AvailabilityTestdata withAvailabilityDescription(String availabilityDescription) {
        this.availabilityDescription = availabilityDescription;
        return this;
    }

    public AvailabilityTestdata withAvailabilityTypeStyleClass(String availabilityTypeStyleClass) {
        this.availabilityTypeStyleClass = availabilityTypeStyleClass;
        return this;
    }


    public Availability create() {
        Availability availability = new Availability(availabilityType, availabilityDescription, availabilityTypeStyleClass);
        return availability;
    }


    public static class SuiteSynthetic extends TestDataSuite<Availability> {
        public Availability AMAR = register(new AvailabilityTestdata()
                .withAvailabilityType(AvailabilityType.AVAILABLE)
                .withAvailabilityDescription("Availability is available for booking")
                .withAvailabilityTypeStyleClass("available")
                .create());

        public Availability THOMAS = register(new AvailabilityTestdata()
                .withAvailabilityType(AvailabilityType.ABSENT)
                .withAvailabilityDescription("As per WAY member is not working, reason could be anything for e.g. vacations, sickness, attending conference etc")
                .withAvailabilityTypeStyleClass("absent")
                .create());

        public Availability WIKTOR = register(new AvailabilityTestdata()
                .withAvailabilityType(AvailabilityType.PUBLIC_HOLIDAY)
                .withAvailabilityDescription("Globally Saturday and Sunday are treated as public holidays. Other than that regional holidays are also public holidays.")
                .withAvailabilityTypeStyleClass("publicHoliday")
                .create());


    }

    private static SuiteSynthetic synthetic;

    public static SuiteSynthetic suiteSynthetic() {
        if (synthetic == null) {
            synthetic = new SuiteSynthetic();
        }
        return synthetic;
    }
}
