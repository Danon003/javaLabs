#include "Image.h"
#include"MedianFilter.h"
#include<iostream>
using namespace std;

int main()
{
    setlocale(LC_ALL, "Russian");
    //====================== ������������� =====================================================================
    string inputFileName1 = "C:\\Users\\user\\source\\repos\\KG_Lab2\\foto1.jpg";
    string inputFileName2 = "C:\\Users\\user\\source\\repos\\KG_Lab2\\foto2.png";
    Image inputImage1;
    Image inputImage2;
    MedianFilter filterGausse(5);
    MedianFilter filterUniform(5);
    if (!inputImage1.isRead(inputFileName1)  ) {
        cerr << "�� ������� ��������� ����������e 1" << endl;
        return 1;
    }
    if (!inputImage2.isRead(inputFileName2)) {
        cerr << "�� ������� ��������� ����������e 2" << endl;
        return 1;
    }
    cout << "����������� ��������� " << endl;
    //==========================================================================================================
    
    //=================== ��������� ����������� � ��������� ����� ==============================================
    Image resultGausseImage = inputImage1; // �������� ������������ �����������
    filterGausse.apply(resultGausseImage);
    string resultGausseFileName = "resultGausse.png";
    if (resultGausseImage.isWrite(resultGausseFileName))
        cout << "����������� � ����������� ����� ���������� � ��������� ��� " << resultGausseFileName << endl;
    else {
        cerr << "�� ������� ��������� ����������� 1" << endl;
        return 1;
    }
    //=========================================================================================================
    
    //==================== ��������� ����������� � ����������� ����� ==========================================
    Image resultUniformImage = inputImage2;
    filterUniform.apply(inputImage2);
    string resultUniformFilename = "resultUniform.png";
    if (resultUniformImage.isWrite(resultUniformFilename))
        cout << "����������� � ����� ������ ���������� � ��������� ��� " << resultUniformFilename << endl;
    else {
        cerr << "�� ������� ��������� ����������� 2" << endl;
        return 1;
    }
    //=========================================================================================================
    cout << "��������� ����������� ���������." << endl;
    system("pause");
    return 0;
}
