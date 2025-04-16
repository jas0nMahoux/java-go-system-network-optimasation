package common

type Concept struct {
	id          string
	title       string
	description string
	isDeleted   bool
}

type Edge struct {
	source string
	target string
}

type Network struct {
	concepts []Concept
	edges    []Edge
}
