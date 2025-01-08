#include <vector>
#include <iostream>
using namespace std;

int main()
{
    int iarr[] = {1, 2, 3, 4, 5};
    // 构造
    vector<int> v1(iarr, iarr + 5); // ivec contains 1,2,3,4,5 区间中的元素，左闭右开
    vector<int> v2(3, 19);          // v2 contains 19,19,19 复制3个19
    vector<int> v3(v1);             // v3 contains a copy of v1 拷贝构造函数
    cout << v1[1] << endl;

    // 构造一个迭代器对象
    for (vector<int>::iterator it = v1.begin(); it != v1.end(); it++)
    {
        cout << *it << endl;
    }
    // 常量迭代器不可被更改
    for (vector<int>::const_iterator it = v1.begin(); it != v1.end(); it++)
    {
        cout << *it << endl;
    }
    return 0;
}
