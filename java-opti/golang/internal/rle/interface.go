package rle

type RLE interface {
	Compress(input string) string
	Decompress(input string) string
}
