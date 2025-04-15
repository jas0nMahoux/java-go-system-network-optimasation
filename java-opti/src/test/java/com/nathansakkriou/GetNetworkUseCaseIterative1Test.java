package com.nathansakkriou;

import com.nathansakkriou.repository.ConceptRepositoryJson;
import com.nathansakkriou.repository.IConceptRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class GetNetworkUseCaseIterative1Test {
    private IConceptRepository conceptRepository;

    @BeforeEach
    public void setUp() {
        conceptRepository = IConceptRepository.createInstanceJson100();
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