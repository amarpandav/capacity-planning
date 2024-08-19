package com.ubs.cpt.service;

import com.smoothie.service.dto.FruitDto;
import com.smoothie.service.searchparams.FruitSearchParameters;

import java.util.List;

/**
 * Service to get smoothies.
 *
 * @author Amar Pandav
 */
public interface UserService {

    /**
     * Find fruits based on search parameters.
     *
     * @param searchParameters fruit related search parameters
     * @return return fruits
     */
    public List<FruitDto> findFruits(FruitSearchParameters searchParameters);

    /**
     * Count the total fruits based on search parameters
     *
     * @param searchParameters fruit related search parameters
     * @return return fruit counts.
     */
    public int countFruits(FruitSearchParameters searchParameters);
}
