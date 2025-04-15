package com.nathansakkriou.entities.concept;

import java.util.UUID;

public record Concept (
        UUID id,
        String title,
        String description
) {
}
