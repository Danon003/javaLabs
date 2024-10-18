#include "MedianFilter.h"

MedianFilter::MedianFilter(int kernelSize) : m_kernelSize(kernelSize){
	m_halfKernelSize = kernelSize / 2;
}

void MedianFilter::apply(Image& image){
	int width = image.getWidth();
	int height = image.getHeight();

	Image tempImage(image);

	for (int y = 0; y < height; ++y)
		for (int x = 0; x < width; ++x){
			int reds[64], greens[64], blues[64]; 
			int count = 0;

			for (int ky = -m_halfKernelSize; ky <= m_halfKernelSize; ++ky) // перебор пикселей от центра €дра
				for (int kx = -m_halfKernelSize; kx <= m_halfKernelSize; ++kx){
					int nx = x + kx;
					int ny = y + ky;

					if (nx >= 0 && nx < width && ny >= 0 && ny < height){ // ѕроверка границ изображени€
						int r, g, b, a;
						tempImage.getPixelColor(nx, ny, r, g, b, a);
						reds[count] = r;
						greens[count] = g;
						blues[count] = b;
						count++;
					}
				}

			// Ќаходим медиану
			int medianR, medianG, medianB;
			getMedian(reds, count, medianR);
			getMedian(greens, count, medianG);
			getMedian(blues, count, medianB);

			image.setPixelColor(x, y, medianR, medianG, medianB, 255);
		}
}

void MedianFilter::getMedian(int* values, int count, int& median){
	for (int i = 0; i < count - 1; ++i)
		for (int j = 0; j < count - i - 1; ++j){
			if (values[j] > values[j + 1]){
				int temp = values[j];
				values[j] = values[j + 1];
				values[j + 1] = temp;
			}
		}

	if (count % 2 == 0)
		median = (values[count / 2 - 1] + values[count / 2]) / 2;
	else
		median = values[count / 2];
}
