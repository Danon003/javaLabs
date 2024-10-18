#include "Image.h"
#include"MedianFilter.h"
#include<iostream>
using namespace std;

int main()
{
    setlocale(LC_ALL, "Russian");
    //====================== ИНИЦИАЛИЗАЦИЯ =====================================================================
    string inputFileName1 = "C:\\Users\\user\\source\\repos\\KG_Lab2\\foto1.jpg";
    string inputFileName2 = "C:\\Users\\user\\source\\repos\\KG_Lab2\\foto2.png";
    Image inputImage1;
    Image inputImage2;
    MedianFilter filterGausse(5);
    MedianFilter filterUniform(5);
    if (!inputImage1.isRead(inputFileName1)  ) {
        cerr << "Не удалось загрузить изображениe 1" << endl;
        return 1;
    }
    if (!inputImage2.isRead(inputFileName2)) {
        cerr << "Не удалось загрузить изображениe 2" << endl;
        return 1;
    }
    cout << "Изображения загружены " << endl;
    //==========================================================================================================
    
    //=================== ОБРАБОТКА ИЗОБРАЖЕНИЯ С ГАУССОВЫМ ШУМОМ ==============================================
    Image resultGausseImage = inputImage1; // Копируем оригинальное изображение
    filterGausse.apply(resultGausseImage);
    string resultGausseFileName = "resultGausse.png";
    if (resultGausseImage.isWrite(resultGausseFileName))
        cout << "Изображение с равномерным шумом обработано и сохранено как " << resultGausseFileName << endl;
    else {
        cerr << "Не удалось сохранить изображение 1" << endl;
        return 1;
    }
    //=========================================================================================================
    
    //==================== ОБРАБОТКА ИЗОБРАЖЕНИЯ С РАВНОМЕРНЫМ ШУМОМ ==========================================
    Image resultUniformImage = inputImage2;
    filterUniform.apply(inputImage2);
    string resultUniformFilename = "resultUniform.png";
    if (resultUniformImage.isWrite(resultUniformFilename))
        cout << "Изображение с шумом Гаусса обработано и сохранено как " << resultUniformFilename << endl;
    else {
        cerr << "Не удалось сохранить изображение 2" << endl;
        return 1;
    }
    //=========================================================================================================
    cout << "Обработка изображений завершена." << endl;
    system("pause");
    return 0;
}
