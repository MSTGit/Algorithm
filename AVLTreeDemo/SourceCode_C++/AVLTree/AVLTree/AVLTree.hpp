//
//  BinaryTree.hpp
//  BinaryTree
//
//  Created by ducktobey on 29/1/2020.
//  Copyright © 2020 ducktobey. All rights reserved.
//

#ifndef BinaryTree_hpp
#define BinaryTree_hpp

#include <stdio.h>
#include <vector>
#include <string>
#include <sstream>
#include <algorithm>
#include <random>
#include <queue>
#include <stack>
#include <map>
#include <math.h>
using namespace std;
template <typename E>
class AVLTree {
    class Node {
    public:
        E m_element;//节点上的元素
        Node *m_left;//左子节点
        Node *m_right;//右子节点
        Node *m_parent;//父节点
        int m_height;//当前节点的高度
        Node() : m_left(nullptr),m_right(nullptr) {}
        Node(E element,Node *parent):m_element(element),m_parent(parent),m_left(nullptr),m_right(nullptr) {};
        ~Node() {
            delete m_left; delete m_right;
            cout << "删除了一个节点:该节点的值为 " << m_element << endl;
        }
        //判断该节点是否为叶子节点
        bool isLeaf() {
            return m_left == NULL && m_right == NULL;
        }
        //是否有左右子树
        bool hasTwoChildren() {
            return m_left != NULL && m_right != NULL;
        }
        
        //是否为左子树
        bool isLeftChild() {
            return m_parent && this == m_parent->m_left;
        }
        //是否为右子树
        bool isRightChild() {
            return m_parent && this == m_parent->m_right;
        }
        
        //更新当前节点的高度
        void updateHeight() {
            int left_Height = m_left ? m_left->m_height : 0;
            int right_Height = m_right ? m_right->m_height : 0;
            m_height = (left_Height > right_Height ? left_Height : right_Height) + 1;
        }
        
        //获取当前节点的平衡因子
        int balanceFactor() {
            int left_Height = m_left ? m_left->m_height : 0;
            int right_Height = m_right ? m_right->m_height : 0;
            return left_Height - right_Height;
        }
        //获取当前节点,高度最高的子节点
        Node *tallerChild() {
            int left_Height = m_left ? m_left->m_height : 0;
            int right_Height = m_right ? m_right->m_height : 0;
            if (left_Height > right_Height) return m_left;
            if (left_Height < right_Height) return m_right;
            //左右子树高度相等,那么就看当前节点是父节点的左子树还是右子树
            return this->isLeftChild() ? m_left : m_right;
        }
        
        int max_depth() const {
            const int left_depth = m_left ? m_left->max_depth() : 0;
            const int right_depth = m_right ? m_right->max_depth() : 0;
            return (left_depth > right_depth ? left_depth : right_depth) + 1;
        }
    };
    //数内部成员变量
    int m_size;
    Node *m_root;//根节点
    void elementNodeNullCheck(E element) {//判断该接地啦是否为空
        if (element == NULL && element != 0) {
            throw "element must not be null";
        }
    }
    Node * node(E element){//通过元素,查找节点
        Node *node = m_root;//从根节点开始查找
        while (node) {
            //cout << "node->m_element = " << node->m_element << " element = " << element << endl;
            if (node->m_element == element) {
                return node;
            } else if (node->m_element > element) {//说明遍历到的当前节点,值大于要找的元素,所以应该往左找
                node = node->m_left;
            } else {
                node = node->m_right;
            }
        }
        //如果找到底,仍然没有找到,说明不存在
        return nullptr;
    }
    void remove(Node *node){//删除节点
        //1.删除叶子节点 直接删除
        //2.删除度为1的节点  子节点替换原节点
        //3.删除度为2的节点 使用,前驱或者后继节点,来覆盖原来节点的值
        if (!node) return;
        cout << "删除元素前:" << endl;
        Dump();
        m_size--;
        if (node->hasTwoChildren()) {
            //找到后继节点
            Node *s = successor(node);
            //使用原来节点的值,覆盖后继节点的值
            node->m_element = s->m_element;
            node = s;
            //删除后继节点
//            if (s->isLeftChild()) {
//                s->m_parent->m_left = nullptr;
//            } else {
//                s->m_parent->m_right = nullptr;
//            }
//            delete s;
//            s = nullptr;
//            cout << "删除元素后:" << endl;
//            Dump();
//            return;
        }
        //度不为2 ,找到子节点
        Node *replacement = node->m_left ? node->m_left : node->m_right;
        if (replacement) {//说明有子节点
            //1.更换parent
            replacement->m_parent = node->m_parent;
            //2.更换子节点的指向
            if (!node->m_parent) {
                //说明node是度为1的节点,并且是根节点
                //1.删除根节点
                
                m_root->m_right = nullptr;
                m_root->m_left = nullptr;
                delete m_root;
                //由于是度为1的节点,所以node只有一个子树,即为replacement
                m_root = replacement;
            } else if (node == node->m_parent->m_left) {//说明是左子树
                
                
                if (node->m_left) {//如果有左子树
                    //将当前节点的左子树指针清空
                    node->m_left = nullptr;

                } else {
                    node->m_right = nullptr;
                }
                //将替换的节点,保存到父节点的左边
                node->m_parent->m_left = replacement;
                //从内存中删除当前的节点
                delete node;
                //对被删除的节点,重新赋值
            } else {
                //清空当前节点的左/右子树
                if (node->m_left) {
                    node->m_left = nullptr;
                } else {
                    node->m_right = nullptr;
                }
                //父节点的右子树重新赋值
                node->m_parent->m_right = replacement;
                //现在node节点没有任何节点指向它,并且它也不指向任何节点,因此将节点从内存中删除
                delete node;
            }
        } else if (node == m_root) {//是只有一个节点的树
            m_root = nullptr;
            delete m_root;
        } else {
            //不是根节点,并且是叶子节点
            
            //1.将父节点的指向清空
            if (node == node->m_parent->m_left) {
                //左子节点
                node->m_parent->m_left = nullptr;
            } else {
                node->m_parent->m_right = nullptr;
            }
            //2.将当前节点删除
            delete node;
        }
        afterRemove(node);
        cout << "删除元素后:" << endl;
        Dump();
    }
    Node * predecessor(Node *node){//找前驱节点
        if (!node) return nullptr;
        Node *p = node->m_left;
        if (p) {//如果有左子树,就查找左子树中,最右的节点
            while (p->m_right) {
                p = p->m_right;
            }
            //直到找到了最右的节点以后,返回该节点,就为前驱节点
            return p;
        }
        //如果没有左子树,就从父节点中继续查找//node == node->m_parent->m_left 表示要在左子树中查找
        while (node->m_parent && node == node->m_parent->m_left) {
            node = node->m_parent;
        }
        //能来到这里,就说明node->m_parent 为空或者node == node->m_parent->m_right.
        return node->m_parent;
    }
    Node * successor(Node *node){//找后继节点<与找前驱节点相反>
        if (!node) return nullptr;
        Node *p = node->m_right;
        if (p) {
            while (p->m_left) {
                p = p->m_left;
            }
            return p;
        }
        while (node->m_parent && node == node->m_parent->m_right) {
            node = node->m_parent;
        }
        return node->m_parent;
    }
    //递归实现
    void preorder(Node *node) {
        if (!node) return;
        cout << "前序遍历->访问了元素 : " << node->m_element << endl;
        preorder(node->m_left);
        preorder(node->m_right);
    }
    //递归实现
    void inorder(Node *node) {
        if (!node) return;
        inorder(node->m_left);
        cout << "中序遍历->访问了元素 : " << node->m_element << endl;
        inorder(node->m_right);
    }
    //递归实现
    void postorder(Node *node) {
        if (!node) return;
        
        postorder(node->m_left);
        postorder(node->m_right);
        cout << "后序遍历->访问了元素 : " << node->m_element << endl;
    }
    int maxDepth(Node *node) {
        if (!node) return 0;
        int leftMax = maxDepth(node->m_left);
        int rightMax = maxDepth(node->m_right);
        return (leftMax > rightMax ? leftMax : rightMax) + 1;
    }
    int m_all_max = 0;
    int minDepth(Node *node) {
        if (!node) return 0;
        int leftMin = minDepth(node->m_left);
        int rightMin = minDepth(node->m_right);
        if (node->m_left && node->m_right) {//既有左子树,又有右子树的节点,选出最下树高
            m_all_max = (m_all_max > (leftMin < rightMin ? leftMin : rightMin) + 1) ? m_all_max : (leftMin < rightMin ? leftMin : rightMin) + 1;
            return (leftMin < rightMin ? leftMin : rightMin) + 1;
        } else {//否则就是计算子树的最小高度
            //m_all_max = (m_all_max > maxDepth(node)) ? m_all_max : maxDepth(node);
            if (node->m_left) {//有左子树
                //计算左边的最小高度
                return leftMin + 1;
            }
            if (node->m_right) {//有右子树
                //计算右边的最小高度
                return rightMin + 1;
            }
            //没有子树,高度为1
            m_all_max = 1;
            return 1;
        }
        
    }
    
    int maxWidth(Node *node) {
        if (!node) return 0;
        
    }
    
    //判断某个节点是否平衡
    bool isBalance(Node *node) {
        return fabs(node->balanceFactor()) <= 1;
    }
    //左旋转
    void rotateLeft(Node *grand) {
        Node *parent = grand->m_right;
        Node *node = parent->m_left;
        grand->m_right = parent->m_left;
        parent->m_left = grand;
        afterRotate(grand, parent, node);
    }
    
    void rotateRight(Node *grand) {
        Node *parent = grand->m_left;
        Node *node = parent->m_right;
        grand->m_left = node;
        parent->m_right = grand;
        afterRotate(grand, parent, node);
    }
    
    //旋转以后,更新这些节点的父子关系
    void afterRotate(Node *grand, Node *parent, Node *child) {
        parent->m_parent = grand->m_parent;
        if (grand->isLeftChild()) {
            grand->m_parent->m_left = parent;
        } else if(grand->isRightChild()){
            grand->m_parent->m_right = parent;
        } else {//根节点
            m_root = parent;
        }
        
        if (child) child->m_parent = grand;
        
        grand->m_parent = parent;
        grand->updateHeight();
        parent->updateHeight();
    }
    
    //将node节点恢复平衡,因为在添加节点的过程中,都会判断新加节点是否平衡,结合文章https://www.jianshu.com/p/a90c186623ac可以知道,最终失衡的节点一定是父节点的父节点
    void rebalance(Node *grand) {
        //cout << " 恢复平衡前:" << endl;
        //Dump();
        
        Node *parent = grand->tallerChild();//高度更高的
        Node *node = parent->tallerChild();
        if (parent->isLeftChild()) {
            if (node->isLeftChild()) {
                //LL的情况
                rotateRight(grand);
            } else {
                //LR的情况
                //1.先向左旋转,变为LL
                rotateLeft(parent);
                //2.将祖父节点向右旋转
                rotateRight(grand);
            }
        } else {
            if (node->isLeftChild()) {
                //RL的情况
                //1.先将父节点向右旋转,变为RR
                rotateRight(parent);
                //2.再将祖父节点向左旋转
                rotateLeft(grand);
            } else {
                //RR的情况
                rotateLeft(grand);
            }
        }
        //cout << " 恢复平衡后:" << endl;
        //Dump();
        cout << endl;
    }
    
    //添加节点以后,整棵树,是否平衡,如果不平衡,就恢复平衡
    void afterAdd(Node *node) {
        while (node) {
            if (isBalance(node)) {//如果当前节点是平衡的,就更新当前节点的高度
                node->updateHeight();
            } else {
                //恢复平衡
                rebalance(node);
                break;
            }
            node = node->m_parent;
        }
    }
    
    //删除节点以后,整棵树是否平衡,如果不平衡,就恢复平衡
    void afterRemove(Node *node) {
        while (node) {
            if (isBalance(node)) {
                node->updateHeight();
            } else {
                rebalance(node);
                break;
            }
            node = node->m_parent;
        }
    }
    int get_max_depth() const { return m_root ? m_root->max_depth() : 0; }
    
    
public:
    AVLTree();//构造函数
    ~AVLTree();//析构函数
    int size();
    bool isEmpty();
    void clear();
    void add(E element);
    void remove(E element);//通过元素,删除节点
    bool contains(E element);
    void preorder();//前序遍历
    void preorder(bool (*func)(E element));//前序遍历,将遍历到的结果返回到外面,并且通过外面,来决定遍历是否停止
    void inorder();//中序遍历
    void inorder(bool (*func)(E element));//中序遍历,将遍历到的结果返回到外面,并且通过外面,来决定遍历是否停止
    void postorder();//后续遍历
    void postorder(bool (*func)(E element));//后续遍历,将遍历到的结果返回到外面,并且通过外面,来决定遍历是否停止
    void levelorder();//层序遍历
    void levelorder(bool (*func)(E element));//层序遍历,将遍历到的结果返回到外面,并且通过外面,来决定遍历是否停止
    bool isComplete();//判断是否为完全二叉树
    int maxDepth();//获取树的最大高度
    int minDepth();//获取树的最小高度
    //int maxWidth();//获取树的最大宽度
    



    
    struct cell_display {
        string   valstr;
        bool     present;
        cell_display() : present(false) {}
        cell_display(std::string valstr) : valstr(valstr), present(true) {}
    };
    
    using display_rows = vector< vector< cell_display > >;
    
    
    
    display_rows get_row_display() const {
        
        
        vector<Node*> traversal_stack;
        vector< std::vector<Node*> > rows;
        if(!m_root) return display_rows();
        
        Node *p = m_root;
        const int max_depth = m_root->max_depth();
        rows.resize(max_depth);
        int depth = 0;
        for(;;) {
            
            
            if(depth == max_depth-1) {
                rows[depth].push_back(p);
                if(depth == 0) break;
                --depth;
                continue;
            }
            
            
            if(traversal_stack.size() == depth) {
                rows[depth].push_back(p);
                traversal_stack.push_back(p);
                if(p) p = p->m_left;
                ++depth;
                continue;
            }
            
            
            if(rows[depth+1].size() % 2) {
                p = traversal_stack.back();
                if(p) p = p->m_right;
                ++depth;
                continue;
            }
            
            
            
            if(depth == 0) break;
            
            traversal_stack.pop_back();
            p = traversal_stack.back();
            --depth;
        }
        
        
        
        display_rows rows_disp;
        std::stringstream ss;
        for(const auto& row : rows) {
            rows_disp.emplace_back();
            for(Node* pn : row) {
                if(pn) {
                    ss << pn->m_element;
                    rows_disp.back().push_back(cell_display(ss.str()));
                    ss = std::stringstream();
                } else {
                    rows_disp.back().push_back(cell_display());
                }   }   }
        return rows_disp;
    }
    
    
    
    vector<string> row_formatter(const display_rows& rows_disp) const {
        using s_t = string::size_type;
        
        
        s_t cell_width = 0;
        for(const auto& row_disp : rows_disp) {
            for(const auto& cd : row_disp) {
                if(cd.present && cd.valstr.length() > cell_width) {
                    cell_width = cd.valstr.length();
                }   }   }
        
        
        if(cell_width % 2 == 0) ++cell_width;
        
        
        vector<string> formatted_rows;
        
        
        
        s_t row_count = rows_disp.size();
        
        
        s_t row_elem_count = 1 << (row_count-1);
        
        
        s_t left_pad = 0;
        
        
        
        for(s_t r=0; r<row_count; ++r) {
            const auto& cd_row = rows_disp[row_count-r-1]; // r reverse-indexes the row
            
            
            s_t space = (s_t(1) << r) * (cell_width + 1) / 2 - 1;
            
            string row;
            
            for(s_t c=0; c<row_elem_count; ++c) {
                
                row += string(c ? left_pad*2+1 : left_pad, ' ');
                if(cd_row[c].present) {
                    
                    const string& valstr = cd_row[c].valstr;
                    
                    
                    s_t long_padding = cell_width - valstr.length();
                    s_t short_padding = long_padding / 2;
                    long_padding -= short_padding;
                    row += string(c%2 ? short_padding : long_padding, ' ');
                    row += valstr;
                    row += string(c%2 ? long_padding : short_padding, ' ');
                } else {
                    
                    row += string(cell_width, ' ');
                }
            }
            
            formatted_rows.push_back(row);
            
            
            if(row_elem_count == 1) break;
            
            s_t left_space  = space + 1;
            s_t right_space = space - 1;
            for(s_t sr=0; sr<space; ++sr) {
                string row;
                for(s_t c=0; c<row_elem_count; ++c) {
                    if(c % 2 == 0) {
                        row += string(c ? left_space*2 + 1 : left_space, ' ');
                        row += cd_row[c].present ? '/' : ' ';
                        row += string(right_space + 1, ' ');
                    } else {
                        row += string(right_space, ' ');
                        row += cd_row[c].present ? '\\' : ' ';
                    }
                }
                formatted_rows.push_back(row);
                ++left_space;
                --right_space;
            }
            left_pad += space + 1;
            row_elem_count /= 2;
        }
        
        
        std::reverse(formatted_rows.begin(), formatted_rows.end());
        
        return formatted_rows;
    }
    
    
    static void trim_rows_left(vector<string>& rows) {
        if(!rows.size()) return;
        auto min_space = rows.front().length();
        for(const auto& row : rows) {
            auto i = row.find_first_not_of(' ');
            if(i==string::npos) i = row.length();
            if(i == 0) return;
            if(i < min_space) min_space = i;
        }
        for(auto& row : rows) {
            row.erase(0, min_space);
        }   }
    
    
    void Dump() const {
        if (m_size == 0) {
            cout << " <empty tree>\n";
            return;
        }
        const int d = get_max_depth();
        
        
        if(d == 0) {
            cout << " <empty tree>\n";
            return;
        }
        
        
        const auto rows_disp = get_row_display();
        
        auto formatted_rows = row_formatter(rows_disp);
        
        trim_rows_left(formatted_rows);
        
        for(const auto& row : formatted_rows) {
            std::cout << ' ' << row << '\n';
        }
    }
    
};
template <typename E>
int AVLTree<E>::size() {
    return m_size;
}

template <typename E>
bool AVLTree<E>::isEmpty() {
    return m_size == 0;
}

template <typename E>
void AVLTree<E>::clear() {
    delete m_root;
    m_root = nullptr;
    m_size = 0;
}

template <typename E>
void AVLTree<E>::add(E element) {
    elementNodeNullCheck(element);
    if (m_root == NULL) {
        //添加的是第一个元素,创建根节点
        m_root = new Node(element,NULL);
        m_size++;
        afterAdd(m_root);
       // m_root->updateHeight();
//        Dump();
//        cout << "当前节点的平衡因子为 : " << m_root->balanceFactor() << endl;
        return;
    }
    //添加的不是第一个节点
    Node *node = m_root;
    Node *parent = m_root;
    //循环找到当前节点的父节点
    while (node != NULL) {
        
        parent = node;
        if (element > node->m_element) {//往右找
            node = node->m_right;
            
        } else if (element < node->m_element) {//往左找
            node = node->m_left;
        } else { //相等 - > 不做处理
            return;
        }
    }
    //
    Node *newNode = new Node(element,parent);
    if (element > parent->m_element) {//添加到右边
        parent->m_right = newNode;
    } else if (element < parent->m_element) {//添加到左边
        parent->m_left = newNode;
    }
    m_size++;
    afterAdd(newNode);
    //newNode->updateHeight();
//    Dump();
//    cout << "当前节点的平衡因子为 : " << m_root->balanceFactor() << endl;
    
}

template <typename E>
void AVLTree<E>::remove(E element) {

    remove(node(element));
    
}

template <typename E>
bool AVLTree<E>::contains(E element) {
    return node(element) != nullptr;
}

template <typename E>
void AVLTree<E>::preorder() {
//    preorder(m_root);//前序遍历->递归实现
//    if (!m_root) return;
    preorder([](E element) -> bool{
        cout << "[非递归]前序遍历->访问了元素 : " << element << endl;
        return false;
    });
}

template <typename E>
void AVLTree<E>::preorder(bool (*func)(E element)) {
    stack<Node *> s;
    Node *node = m_root;
    while (node || !s.empty()) {//如果节点不为空或者栈不为空,如果node为null并且栈已经空了,说明就已经遍历完了
        while (node) {
            //先访问元素
            //cout << "[非递归]前序遍历->访问了元素 : " << node->m_element << endl;
            if (func(node->m_element)) {
                return;
            }
            //访问完了以后,将节点入栈
            s.push(node);
            //下一次访问左子树
            node = node->m_left;
        }
        //到这里,说明当前子树的左子树已经访问完了,开始访问右子树
        //左子树访问完了,然后依次访问栈中的右元素,并且在访问右子树时,子树有左子树,又会继续先访问左子树
        if (!s.empty()) {
            //弹出栈顶元素
            node = s.top();
            s.pop();
            node = node->m_right;
        }
    }
}

template <typename E>
void AVLTree<E>::inorder() {
//    inorder(m_root);//中序遍历->递归实现
    inorder([](E element) {
       cout << "[非递归]中序遍历->访问了元素 : " << element << endl;
        return false;
    });
}
template <typename E>
void AVLTree<E>::inorder(bool (*func)(E)) {
    if (!m_root) return;
    stack<Node *> s;
    Node *node = m_root;
    while (node || !s.empty()) {
        while (node) {
            s.push(node);
            node = node->m_left;//先把左子树入栈
        }
        
        if (!s.empty()) {
            node = s.top();
            //栈顶的元素,始终是当前栈中值最小的元素
            if (func(node->m_element)) {
                return;
            }
            s.pop();
            node = node->m_right;
        }
    }
}

template <typename E>
void AVLTree<E>::postorder() {
    postorder(m_root);//后序遍历->递归实现
    postorder([](E element) {
        cout << "[非递归]后序遍历->访问了元素 : " << element << endl;
        return false;
    });
}

template <typename E>
void AVLTree<E>::postorder(bool (*func)(E)) {
    if (!m_root) return;
    stack<Node *> s;
    map<Node *, bool> visited;//用来表示该节点是否访问过
    Node *node = m_root;
    while (node || !s.empty()) {
        while (node && !visited[node]) {
            s.push(node);
            node = node->m_left;
        }
        if (!s.empty()) {
            
            node = s.top();//这个节点,此时为栈中最大的元素
            
            if (!visited[node]) {
                visited[node] = true;
                node = node->m_right;
            } else {
                if (func(node->m_element)) {
                    return;
                }
                s.pop();
            }
            
        }
    }
}
template <typename E>
void AVLTree<E>::levelorder() {
    levelorder([](E element) {
        cout << "层序遍历->访问了元素 : " << element << endl;
        return false;
    });
}

template <typename E>
void AVLTree<E>::levelorder(bool (*func)(E)) {
    if (!m_root) return;
    queue<Node *> q;
    q.push(m_root);
    while (q.size() != 0) {
        //拿出元素进行访问
        Node *node = q.front();
        q.pop();
        if (func(node->m_element)) {
            return;
        }
        //然后把当前节点的左右子树进行入队
        if (node->m_left) q.push(node->m_left);
        if (node->m_right) q.push(node->m_right);
    }
}

template <typename E>
bool AVLTree<E>::isComplete() {
    //如果树为空,说明不是一颗完全二叉树
    if (m_size == 0 || m_root == nullptr) return false;
    queue<Node *> q;
    q.push(m_root);
    bool leaf = false;
    while (q.size() != 0) {
        Node *node = q.front();
        q.pop();
        if (leaf && !node->isLeaf()) return false;//本来应该为叶子节点,但是当前节点并不是叶子节点时
        if (node->m_left) {
            q.push(node->m_left);
        } else if (node->m_right) {
            //没有进入到上一个if判断,说明有右子树,但是没有左子树,那么就不符合完全二叉树的性质
            return false;
        }
        if (node->m_right) {
            q.push(node->m_right);
        } else {
            //能进入这里,说明没有左子树,也没有右子树,那么就应该为叶子节点
            leaf = true;
        }
    }
    return true;
}

template <typename E>
int AVLTree<E>::maxDepth() {
    return maxDepth(m_root);
}

template <typename E>
int AVLTree<E>::minDepth() {
    int min_Depth = minDepth(m_root);
    cout << "所有节点最小高度中,最大高度 : " << m_all_max << endl;
    return min_Depth;
}

template <typename E>
AVLTree<E>::~AVLTree() {
    
}

template <typename E>
AVLTree<E>::AVLTree() {
    
}


#endif /* BinaryTree_hpp */
