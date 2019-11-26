#pragma once
#include <iostream>
template <typename E>

class ArrayList {
public:
	int size();
	bool isEmpty();
	bool contains(E element);
	void add(E element);
	E get(int index);
	E set(int index, E element);
	void add(int index, E element);
	E remove(int index);
	int indexOf(E element);
	void clear();
};

