package rle

import "testing"

func rLEBenchmarkCompress(rle RLE, b *testing.B) {
	input := "aaaaaabbbbaaaaaaabbbbbbbbbaaaaaaaabbbbbb"
	for b.Loop() {
		rle.Compress(input)
	}
}

func rLEBenchmarkDecompress(rle RLE, b *testing.B) {
	input := "a6b4a8b8a10b6"
	for b.Loop() {
		rle.Decompress(input)
	}
}

func BenchmarkRLECompressNaive(b *testing.B) {
	rle := NewNaive()
	rLEBenchmarkCompress(rle, b)
}

func BenchmarkRLEDecompressNaive(b *testing.B) {
	rle := NewNaive()
	rLEBenchmarkDecompress(rle, b)
}

func BenchmarkRLECompressParallel(b *testing.B) {
	rle := NewParallel()
	rLEBenchmarkCompress(rle, b)
}

func BenchmarkRLEDecompressParallel(b *testing.B) {
	rle := NewParallel()
	rLEBenchmarkDecompress(rle, b)
}
