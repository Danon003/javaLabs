#pragma once

#include <string>
#include <memory>

class Image
{
public:
	Image();
	Image(int width, int height, int nChannels);
	Image(const std::string& path);
	Image(const Image& image);
	~Image();

	Image& operator=(const Image& image);

	// struct RGB;
	void getPixelColor(int col, int row, int& red, int& green, int& blue, int& alpha) const;
	void setPixelColor(int col, int row, int red, int green, int blue, int alpha);

	bool isRead(const std::string& path);
	bool isWrite(const std::string& path);

	int getWidth() const{
		return m_width;
	}

	int getHeight() const{
		return m_height;
	}

private:
	int m_width;
	int m_height;

	int m_nChannels;

	int m_size;

	std::unique_ptr<uint8_t[]> m_data;
};
