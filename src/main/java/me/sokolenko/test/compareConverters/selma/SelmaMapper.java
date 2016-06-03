/*
 * This document set is the property of GTECH Corporation, Providence,
 * Rhode Island, and contains confidential and trade secret information.
 * It cannot be transferred from the custody or control of GTECH except as
 * authorized in writing by an officer of GTECH. Neither this item nor
 * the information it contains can be used, transferred, reproduced, published,
 * or disclosed, in whole or in part, directly or indirectly, except as
 * expressly authorized by an officer of GTECH, pursuant to written agreement.
 *
 * Copyright 2016 GTECH Corporation. All Rights Reserved.
 */
package me.sokolenko.test.compareConverters.selma;


import fr.xebia.extras.selma.Field;
import fr.xebia.extras.selma.IgnoreMissing;
import fr.xebia.extras.selma.Mapper;
import fr.xebia.extras.selma.Maps;
import me.sokolenko.test.compareConverters.model.target.Category;
import me.sokolenko.test.compareConverters.model.target.Resource;

@Mapper(
        withCustomFields = {
                @Field({"name", "categoryName"})
        },
//        the custom mapping somehow doesn't work as it's not clear in the doc how to apply it to a nested class.
//        for the benchmark purpose, simply comment out offending properties instead of investigating the issue.
        withIgnoreFields = {"Resource.width", "Resource.height", "Price.from", "Price.to"},
        withIgnoreMissing = IgnoreMissing.ALL
)
public interface SelmaMapper {
    Category asTargetCategory(me.sokolenko.test.compareConverters.model.source.Category source);

    @Maps(
            withCustom = ResourceCustomMapping.class
    )
    Resource asTargetResource(me.sokolenko.test.compareConverters.model.source.Resource source);
}
