package com.nathansakkriou;

import com.nathansakkriou.repository.ConceptRepository;
import com.nathansakkriou.repository.IConceptRepository;
import junit.framework.TestCase;
import junit.framework.TestResult;
import org.apache.commons.math3.ml.neuralnet.Network;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class GetNetworkUseCaseIterative1Test {
    private IConceptRepository conceptRepository;

    @BeforeEach
    public void setUp() {
        conceptRepository = new ConceptRepository();
    }

    @Test
    public void test_ok() {
        // GIVEN
        GetNetworkUseCaseIterative1 usecase = new GetNetworkUseCaseIterative1(conceptRepository);

        // WHEN
        var network = usecase.execute();

        // THEN
        Assertions.assertNotNull(network);
    }
}