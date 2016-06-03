package me.sokolenko.test.compareConverters.selma;

import fr.xebia.extras.selma.Selma;
import me.sokolenko.test.compareConverters.Converter;
import me.sokolenko.test.compareConverters.model.source.Category;

/**
 * @author Anatoliy Sokolenko
 */
public class SelmaConverter implements Converter {

    private final SelmaMapper mapper;

    public SelmaConverter() {
        // Get SelmaMapper
        mapper = Selma.builder(SelmaMapper.class).build();
    }

    @Override
    public me.sokolenko.test.compareConverters.model.target.Category map(Category source) {
        return mapper.asTargetCategory(source);
    }
}
