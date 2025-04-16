package rle

import "testing"

func RLECompressTest(rle RLE, t *testing.T) {
	// GIVEN
	input := "aabbcc"
	expected := "a2b2c2"

	// WHEN
	result := rle.Compress(input)

	// THEN
	if result != expected {
		t.Errorf("Expected %s, but got %s", expected, result)
	}
}

func RLEDecompressTest(rle RLE, t *testing.T) {
	// GIVEN
	input := "a2b2c2"
	expected := "aabbcc"

	// WHEN
	result := rle.Decompress(input)

	// THEN
	if result != expected {
		t.Errorf("Expected %s, but got %s", expected, result)
	}
}

func TestNaiveCompress(t *testing.T) {
	rle := NewNaive()
	RLECompressTest(rle, t)
}

func TestNaiveDecompress(t *testing.T) {
	rle := NewNaive()
	RLEDecompressTest(rle, t)
}

func TestParallelCompress(t *testing.T) {
	rle := NewParallel()
	RLECompressTest(rle, t)
}

func TestParallelDecompress(t *testing.T) {
	rle := NewParallel()
	RLEDecompressTest(rle, t)
}
