package common

type ConceptRepository interface {
	FindAll() []Concept
	FindAllExcludeDeleted() []Concept
}

type ConceptRepositoryImpl struct {
	concepts []Concept
}

func (c ConceptRepositoryImpl) FindAll() []Concept {
	return c.concepts
}

func (c ConceptRepositoryImpl) FindAllExcludeDeleted() []Concept {
	filtered := make([]Concept, len(c.concepts))
	for _, concept := range c.concepts {
		if concept.isDeleted {
			filtered = append(filtered, concept)
		}
	}

	return filtered
}
