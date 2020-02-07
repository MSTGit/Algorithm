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
using namespace std;
template <typename E>
class RedBlackTree {
    class Node {
    public:
        E m_element;//节点上的元素
        Node *m_left = nullptr;//左子节点
        Node *m_right = nullptr;//右子节点
        Node *m_parent = nullptr;//父节点
        bool m_color = false;//表示红色
        Node() : m_left(nullptr),m_right(nullptr) {}
        Node(E element,Node *parent):m_element(element),m_parent(parent),m_left(nullptr),m_right(nullptr) {};
        ~Node() {
            delete m_left; delete m_right;

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
        
        //获取当前节点的兄弟节点
        Node *sibling() {
            
            if (isLeftChild()) {//返回右子树
                return m_parent->m_right;
            }
            
            if (isRightChild()) {//返回左子树
                return m_parent->m_left;
            }
            
            //没有父节点,所以不存在兄弟节点
            return nullptr;
        }
        
        int max_depth() const {
            const int left_depth = m_left ? m_left->max_depth() : 0;
            const int right_depth = m_right ? m_right->max_depth() : 0;
            return (left_depth > right_depth ? left_depth : right_depth) + 1;
        }
    };
    //数内部成员变量
    int m_size = 0;
    Node *m_root = nullptr;//根节点
    bool m_red = false;
    bool m_black = true;
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

        m_size--;
        if (node->hasTwoChildren()) {
            //找到后继节点
            Node *s = successor(node);
            //使用原来节点的值,覆盖后继节点的值
            node->m_element = s->m_element;
            node = s;
//            s = nullptr;
//            //删除后继节点
//            delete s;
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
            afterRemove(replacement);
        } else if (node == m_root) {//是只有一个节点的树
            
            m_root = nullptr;
            afterRemove(node);
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
            afterRemove(node);
            //2.将当前节点删除
            delete node;
        }
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
    
    //左旋转
    void rotateLeft(Node *grand) {
        Node *parent = grand->m_right;
        Node *node = parent->m_left;
        grand->m_right = parent->m_left;
        parent->m_left = grand;
        afterRotate(grand, parent, node);
    }
    //右旋转
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
    }
    
    //给节点染色
    Node *color(Node *node,bool color) {
        if (!node) return node;
        node->m_color = color;
        return node;
    }
    
    //将节点染成红色
    Node *red(Node *node) {
        node->m_color = m_red;
        return node;
    }
    
    //将节点染成黑色
    Node *black(Node *node) {
        node->m_color = m_black;
        return node;
    }
    
    //获取当前节点的颜色
    bool colorOf(Node *node) {
        return node ? node->m_color : m_black;
    }
    
    //判断当前节点是否为黑色
    bool isBlack(Node *node) {
        return colorOf(node) == m_black;
    }
    
    //判断当前节点是否为红色
    bool isRed(Node *node) {
        return colorOf(node) == m_red;
    }
    
    //添加节点以后,整棵树,是否平衡,如果不平衡,就恢复平衡
    void afterAdd(Node *node) {
        Node *parent = node->m_parent;
        if (!parent) {
            //说明是根节点或者上溢到了根节点.直接将该节点染成黑色
            black(node);
            return;
        }
        
        //如果父节点是黑色,则不需要处理
        if (isBlack(parent)) return;
        //拿到叔父节点
        Node *uncle = parent->sibling();
        //拿到祖父节点
        Node *grand = parent->m_parent;
        //1.由于前面已经处理了父节点为黑色的情况,所以,能到这里,父节点一定是红色,如果叔父节点也是红色,说明新加节点以后,会导致上溢
        if (isRed(uncle)) {
            //将parent染成黑色,成为一个单独的B树节点
            black(parent);
            //将cuncle染成黑色,成为一个单独的B树节点
            black(uncle);
            //将grand节点染为红色,并且作为新添加的节点,进行操作,上溢时,grand,uncle,parent之间的关系不变
            red(grand);
            afterAdd(grand);
            return;
        }
        
        //能到这里,叔父节点一定不是红色,并且父节点一定是红色
        if (parent->isLeftChild()) {
            if (node->isLeftChild()) {
                //LL的情况,对祖父节点进行右旋转
                rotateRight(grand);
                //把自己染红
                red(node);
                //把父节点染黑
                black(parent);
                //把祖父节点染红
                red(grand);
            } else {
                //LR的情况,先对父节点进行左旋转,在对祖父节点进行右旋转
                rotateLeft(parent);
                rotateRight(grand);
                //把自己染黑
                black(node);
                //把祖父节点染红
                red(grand);
            }
        } else {
            if (node->isLeftChild()) {
                //RL的情况,先对父节点进行右旋转,再对祖父节点进行左旋转
                rotateRight(parent);
                rotateLeft(grand);
                //把自己染黑
                black(node);
                //把祖父节点染红
                red(grand);
            } else {
                //RR的情况,对祖父节点进行左旋转
                rotateLeft(grand);
                //把自己染红
                red(node);
                //把父节点染黑
                black(parent);
                //把祖父节点染红
                red(grand);
            }
        }
    }
    
    //删除节点以后,整棵树是否平衡,如果不平衡,就恢复平衡
    void afterRemove(Node *node) {
        if (isRed(node)) {//删除的是叶子节点或者用于取代node的子节点是红色
            black(node);//如果用于取代node的子节点是红色,直接染黑,删除的是叶子节点时,染黑了也无所谓,因为该节点马上就会从内存中消失
            return;
        }
        
        Node *parent = node->m_parent;
        if (!parent) return;//不存在父节点,说明删除的是根节点
        //能来到这里,说明删除的一定是黑色节点,则会导致下溢
        
        //1.先判断删除的节点是左还是右 (parent->m_left == null 说明前面左边被清空了)
        bool left = parent->m_left == nullptr || node->isLeftChild();
        //2.拿到兄弟节点
        Node *sibling = left ? parent->m_right : parent->m_left;
        if (!left) {
            //删除的节点在左边,则兄弟节点在右边
            if (isRed(sibling)) {//如果兄弟节点是红色,先把兄弟节点变为黑色,然后到if后面统一处理
                //1.对parent进行右旋转
                rotateRight(parent);
                //2.兄弟节点染黑
                black(sibling);
                //3.parent节点染红
                red(parent);
                //4.旋转以后,兄弟节点发生了变化,所以更新兄弟节点的值
                sibling = parent->m_left;
                //通过上面步骤的处理以后,就可以在后面按照黑兄弟的流程统一处理了
            }
            //到这里,兄弟均为黑色
            //1.看一下兄弟节点是否有至少一个红色子节点
            if (isBlack(sibling->m_left) && isBlack(sibling->m_right)) {
                //表示兄弟节点的左右节点,都是黑色的,不能借,所以只能父节点向下与兄弟节点合并
                
                //1.在染色之前,判断父节点是什么颜色
                bool parentBlack = isBlack(parent);//因为可能是根节点下溢的情况
                //2.父节点染黑
                black(parent);
                //3.兄弟节点染红
                red(sibling);
                if (parentBlack) {
                    afterRemove(parent);
                }
            } else {
                //兄弟节点,至少有一个红色子节点
                //1.如果兄弟节点的左边是黑,兄弟要先左旋转
                if (isBlack(sibling->m_left)) {
                    rotateLeft(sibling);
                    //由于做了旋转,所以对兄弟重新赋值
                    sibling = parent->m_left;
                }
                //2.先将要中间的节点(兄弟)染为parent的颜色
                color(sibling, colorOf(parent));
                //3.中间节点的左右,染黑
                black(sibling->m_left);//sibling左边
                black(parent);//旋转后sibling的右边
                //4.对parent进行右旋转
                rotateRight(parent);
            }
            
        } else {
            //与上面的操作是对称的
            if (isRed(sibling)) {
                rotateLeft(parent);
                black(sibling);
                red(parent);
                sibling = parent->m_right;
            }
            if (isBlack(sibling->m_left) && isBlack(sibling->m_right)) {

                bool parentBlack = isBlack(parent);
                black(parent);
                red(sibling);
                if (parentBlack) {
                    afterRemove(parent);
                }
            } else {
                if (isBlack(sibling->m_right)) {
                    rotateRight(sibling);
                    sibling = parent->m_right;
                }
                color(sibling, colorOf(parent));
                black(sibling->m_right);//sibling左边
                black(parent);//旋转后sibling的右边
                rotateLeft(parent);
            }
        }
        
    }
    
    
public:
    RedBlackTree();//构造函数
    ~RedBlackTree();//析构函数
    int size();
    bool isEmpty();
    void clear();
    void add(E element);
    void remove(E element);//通过元素,删除节点
    bool contains(E element);
    void preorder();//前序遍历
    template<typename Functor>
    void preorder(Functor functor){//前序遍历,将遍历到的结果返回到外面,并且通过外面,来决定遍历是否停止
        stack<Node *> s;
        Node *node = m_root;
        while (node || !s.empty()) {//如果节点不为空或者栈不为空,如果node为null并且栈已经空了,说明就已经遍历完了
            while (node) {
                //先访问元素
                //cout << "[非递归]前序遍历->访问了元素 : " << node->m_element << endl;
                if (functor(node->m_element)) {
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
    void inorder();//中序遍历
    template<typename Functor>
    void inorder(Functor functor){//中序遍历,将遍历到的结果返回到外面,并且通过外面,来决定遍历是否停止
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
                if (functor(node->m_element)) {
                    return;
                }
                s.pop();
                node = node->m_right;
            }
        }
    }
    void postorder();//后续遍历
    template<typename Functor>
    void postorder(Functor functor){//后续遍历,将遍历到的结果返回到外面,并且通过外面,来决定遍历是否停止
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
                    if (functor(node->m_element)) {
                        return;
                    }
                    s.pop();
                }
                
            }
        }
    }
    void levelorder();//层序遍历
    template<typename Functor>
    void levelorder(Functor functor){//层序遍历,将遍历到的结果返回到外面,并且通过外面,来决定遍历是否停止
        if (!m_root) return;
        queue<Node *> q;
        q.push(m_root);
        while (q.size() != 0) {
            //拿出元素进行访问
            Node *node = q.front();
            q.pop();
            if (functor(node->m_element)) {
                return;
            }
            //然后把当前节点的左右子树进行入队
            if (node->m_left) q.push(node->m_left);
            if (node->m_right) q.push(node->m_right);
        }
    }
    bool isComplete();//判断是否为完全二叉树
    int maxDepth();//获取树的最大高度
    int minDepth();//获取树的最小高度
    int maxWidth();//获取树的最大宽度
    int get_max_depth() const { return m_root ? m_root->max_depth() : 0; }



    
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
int RedBlackTree<E>::size() {
    return m_size;
}

template <typename E>
bool RedBlackTree<E>::isEmpty() {
    return m_size == 0;
}

template <typename E>
void RedBlackTree<E>::clear() {
    delete m_root;
    m_root = nullptr;
    m_size = 0;
}

template <typename E>
void RedBlackTree<E>::add(E element) {
    elementNodeNullCheck(element);
    if (m_root == NULL) {
        //添加的是第一个元素,创建根节点
        m_root = new Node(element,NULL);
        m_size++;
        afterAdd(m_root);
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
}

template <typename E>
void RedBlackTree<E>::remove(E element) {

    remove(node(element));
}

template <typename E>
bool RedBlackTree<E>::contains(E element) {
    return node(element) != nullptr;
}

template <typename E>
void RedBlackTree<E>::preorder() {
//    preorder(m_root);//前序遍历->递归实现
//    if (!m_root) return;
    preorder([](E element) -> bool{
        cout << "[非递归]前序遍历->访问了元素 : " << element << endl;
        return false;
    });
}

template <typename E>
void RedBlackTree<E>::inorder() {
//    inorder(m_root);//中序遍历->递归实现
    inorder([](E element) {
       cout << "[非递归]中序遍历->访问了元素 : " << element << endl;
        return false;
    });
}

template <typename E>
void RedBlackTree<E>::postorder() {
    postorder(m_root);//后序遍历->递归实现
    postorder([](E element) {
        cout << "[非递归]后序遍历->访问了元素 : " << element << endl;
        return false;
    });
}

template <typename E>
void RedBlackTree<E>::levelorder() {
    levelorder([](E element) {
        cout << "层序遍历->访问了元素 : " << element << endl;
        return false;
    });
}

template <typename E>
bool RedBlackTree<E>::isComplete() {
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
int RedBlackTree<E>::maxDepth() {
    return maxDepth(m_root);
}

template <typename E>
int RedBlackTree<E>::minDepth() {
    int min_Depth = minDepth(m_root);
    cout << "所有节点最小高度中,最大高度 : " << m_all_max << endl;
    return min_Depth;
}

template <typename E>
RedBlackTree<E>::~RedBlackTree() {
    
}

template <typename E>
RedBlackTree<E>::RedBlackTree() {
    
}


#endif /* BinaryTree_hpp */
