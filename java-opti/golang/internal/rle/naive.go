package rle

type Naive struct {
}

func NewNaive() Naive {
	return Naive{}
}

func (n Naive) Compress(input string) string {
	if len(input) == 0 {
		return ""
	}

	result := ""
	count := 1

	for i := 1; i < len(input); i++ {
		if input[i] == input[i-1] {
			count++
		} else {
			result += string(input[i-1]) + string(rune(count+'0'))
			count = 1
		}
	}

	result += string(input[len(input)-1]) + string(rune(count+'0'))

	return result
}

func (n Naive) Decompress(input string) string {
	result := ""
	count := 0
	for i := 0; i < len(input); i++ {
		if i+1 < len(input) && input[i+1] >= '0' && input[i+1] <= '9' {
			count = int(input[i+1] - '0')
			for j := 0; j < count; j++ {
				result += string(input[i])
			}
			i++
		} else {
			result += string(input[i])
		}
	}

	return result
}
