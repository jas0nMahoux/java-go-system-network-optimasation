package rle

import (
	"strings"
	"sync"
)

type Parallel struct{}

func NewParallel() Parallel {
	return Parallel{}
}

func (p Parallel) Compress(input string) string {
	if len(input) == 0 {
		return ""
	}

	// Divise l'entrée en chunks
	chunkSize := len(input) / 4
	if chunkSize < 1 {
		chunkSize = 1
	}

	var wg sync.WaitGroup
	results := make(chan string, 4)

	// Traite chaque chunk en parallèle
	for i := 0; i < len(input); i += chunkSize {
		wg.Add(1)
		end := i + chunkSize
		if end > len(input) {
			end = len(input)
		}

		go func(chunk string) {
			defer wg.Done()

			var sb strings.Builder
			count := 1

			for j := 1; j < len(chunk); j++ {
				if chunk[j] == chunk[j-1] {
					count++
				} else {
					sb.WriteByte(chunk[j-1])
					sb.WriteByte(byte(count + '0'))
					count = 1
				}
			}

			sb.WriteByte(chunk[len(chunk)-1])
			sb.WriteByte(byte(count + '0'))

			results <- sb.String()
		}(input[i:end])
	}

	// Attend la fin des goroutines et ferme le canal
	go func() {
		wg.Wait()
		close(results)
	}()

	// Combine les résultats
	var finalResult strings.Builder
	for r := range results {
		finalResult.WriteString(r)
	}

	return finalResult.String()
}

func (p Parallel) Decompress(input string) string {
	if len(input) == 0 {
		return ""
	}

	var wg sync.WaitGroup
	results := make(chan string, 4)

	// Divise l'entrée en segments (caractère + nombre)
	segments := make([]string, 0)
	for i := 0; i < len(input); i++ {
		if i+1 < len(input) && input[i+1] >= '0' && input[i+1] <= '9' {
			segments = append(segments, input[i:i+2])
			i++
		}
	}

	// Traite chaque segment en parallèle
	chunkSize := len(segments) / 4
	if chunkSize < 1 {
		chunkSize = 1
	}

	for i := 0; i < len(segments); i += chunkSize {
		wg.Add(1)
		end := i + chunkSize
		if end > len(segments) {
			end = len(segments)
		}

		go func(segs []string) {
			defer wg.Done()
			var sb strings.Builder

			for _, seg := range segs {
				char := seg[0]
				count := int(seg[1] - '0')
				sb.WriteString(strings.Repeat(string(char), count))
			}

			results <- sb.String()
		}(segments[i:end])
	}

	go func() {
		wg.Wait()
		close(results)
	}()

	var finalResult strings.Builder
	for r := range results {
		finalResult.WriteString(r)
	}

	return finalResult.String()
}
