#include "ArrayList.h"
using namespace std;
template <typename E>
int ArrayList<E>::size() { cout << "size" << endl; return 0; }
template <typename E>
bool ArrayList<E>::isEmpty() { return true; }
template <typename E>
bool ArrayList<E>::contains(E element) { return true; }
template <typename E>
void ArrayList<E>::add(E element) {}
template <typename E>
E ArrayList<E>::get(int index) { return NULL; }
template <typename E>
E ArrayList<E>::set(int index, E element) { return NULL; }
template <typename E>
void ArrayList<E>::add(int index, E element) {}
template <typename E>
E ArrayList<E>::remove(int index) { return NULL; }
template <typename E>
int ArrayList<E>::indexOf(E element) { return 0; }
template <typename E>
void ArrayList<E>::clear() {}