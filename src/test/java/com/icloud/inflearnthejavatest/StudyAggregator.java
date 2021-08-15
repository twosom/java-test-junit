package com.icloud.inflearnthejavatest;

import com.icloud.inflearnthejavatest.domain.Study;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.params.aggregator.ArgumentsAccessor;
import org.junit.jupiter.params.aggregator.ArgumentsAggregationException;
import org.junit.jupiter.params.aggregator.ArgumentsAggregator;

public class StudyAggregator implements ArgumentsAggregator {


    @Override
    public Object aggregateArguments(ArgumentsAccessor accessor,
                                     ParameterContext context) throws ArgumentsAggregationException {
        return new Study(accessor.getInteger(0), accessor.getString(1));
    }
}
