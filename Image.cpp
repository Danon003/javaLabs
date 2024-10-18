#define _CRT_SECURE_NO_WARNINGS
#define STB_IMAGE_IMPLEMENTATION
#define STB_IMAGE_WRITE_IMPLEMENTATION

#include "Image.h"

#include "stb_image.h"
#include "stb_image_write.h"

Image::Image() : m_width(0), m_height(0), m_nChannels(0), m_size(0), m_data(nullptr) {}

Image::Image(int width, int height, int nChannels) : m_width(width), m_height(height), m_nChannels(nChannels){
	m_size = width * height * nChannels;
	m_data = std::make_unique<uint8_t[]>(m_size);
	std::memset(m_data.get(), 0, m_size);
}

Image::Image(const std::string& path){
	m_data.reset(stbi_load(path.c_str(), &m_width, &m_height, &m_nChannels, 0));
	if (m_data)
		m_size = m_width * m_height * m_nChannels;
}

Image::Image(const Image& image){
	m_width = image.m_width;
	m_height = image.m_height;
	m_nChannels = image.m_nChannels;
	m_size = image.m_size;
	m_data.reset(new uint8_t[m_size]);
	std::memcpy(m_data.get(), image.m_data.get(), m_size);
}

Image& Image::operator=(const Image& image){
	if (this != &image){
		m_width = image.m_width;
		m_height = image.m_height;
		m_nChannels = image.m_nChannels;
		m_size = image.m_size;
		m_data = std::make_unique<uint8_t[]>(m_size);
		std::memcpy(m_data.get(), image.m_data.get(), m_size);
	}
	return *this;
}

void Image::getPixelColor(int col, int row, int& red, int& green, int& blue, int& alpha) const{
	if (col < 0 || col >= m_width || row < 0 || row >= m_height){
		red = green = blue = alpha = 0;
		return;
	}
	int index = (row * m_width + col) * m_nChannels;
	red = (m_nChannels > 0) ? m_data[index] : 0;
	green = (m_nChannels > 1) ? m_data[index + 1] : 0;
	blue = (m_nChannels > 2) ? m_data[index + 2] : 0;
	alpha = (m_nChannels > 3) ? m_data[index + 3] : 255;
}

void Image::setPixelColor(int col, int row, int red, int green, int blue, int alpha){
	if (col < 0 || col >= m_width || row < 0 || row >= m_height)
		return;

	int index = (row * m_width + col) * m_nChannels;
	if (m_nChannels > 0)
		m_data[index] = static_cast<uint8_t>(red);
	if (m_nChannels > 1)
		m_data[index + 1] = static_cast<uint8_t>(green);
	if (m_nChannels > 2)
		m_data[index + 2] = static_cast<uint8_t>(blue);
	if (m_nChannels > 3)
		m_data[index + 3] = static_cast<uint8_t>(alpha);
}

bool Image::isRead(const std::string& path){
	m_data.reset(stbi_load(path.c_str(), &m_width, &m_height, &m_nChannels, 0));
	if (m_data)
		m_size = m_width * m_height * m_nChannels;
	return (m_data != nullptr);
}

bool Image::isWrite(const std::string& path){
	return stbi_write_png(path.c_str(), m_width, m_height, m_nChannels, m_data.get(), 0);
}

Image::~Image(){
	m_data.reset(nullptr);
}





