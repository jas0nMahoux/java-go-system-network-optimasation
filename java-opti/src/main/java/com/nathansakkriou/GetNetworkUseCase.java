package com.nathansakkriou;

import com.nathansakkriou.entities.network.NetworkConcepts;
import com.nathansakkriou.repository.IConceptRepository;

public class GetNetworkUseCase {
    private final IConceptRepository conceptRepository;

    public GetNetworkUseCase(IConceptRepository conceptRepository) {
        this.conceptRepository = conceptRepository;
    }

    public NetworkConcepts execute() {
        return null;
    }
}
