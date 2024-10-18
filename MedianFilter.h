#pragma once

#include "Image.h"

class MedianFilter{
public:
    MedianFilter(int kernelSize);
    void apply(Image& image);

private:
    int m_kernelSize;
    int m_halfKernelSize;

    void getMedian(int* values, int count, int& median);
};