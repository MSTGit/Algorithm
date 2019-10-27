package com.xd.Set;

import com.xd.Set.file.FileInfo;
import com.xd.Set.file.Files;

import java.io.File;

public class SetDemo {
    public static void main(String[] args) {
        treeSetTest();
//        compareListSetAndTreeSet();
    }

    static void compareListSetAndTreeSet(){
        FileInfo fileInfo = Files.read("C:\\Users\\T\\Desktop\\数据结构和算法\\Algorithm\\src\\com\\xd\\Set",new String[]{"java"});
        System.out.println("文件数量：" + fileInfo.getFiles());
        System.out.println("代码行数：" + fileInfo.getLines());
        String[] words = fileInfo.words();
        System.out.println("单词数量：" + words.length);
        Times.test("ListSet", new Times.Task() {
            @Override
            public void execute() {
                handle(new ListSet<>(),words);
            }
        });

        Times.test("ListSet", new Times.Task() {
            @Override
            public void execute() {
                handle(new TreeSet<>(),words);
            }
        });
    }

    static void handle(Set<String> set, String[] words){
        for (int i = 0; i < words.length; i++) {
            set.add(words[i]);
        }
        for (int i = 0; i < words.length; i++) {
            set.contains(words[i]);
        }
        for (int i = 0; i < words.length; i++) {
            set.remove(words[i]);
        }
    }

    static void listSetTest(){
        Set<Integer> listSet = new ListSet<>();
        listSet.add(12);
        listSet.add(13);
        listSet.add(10);
        listSet.add(11);
        listSet.add(10);
        listSet.traversal(new Set.Visitor<Integer>() {
            @Override
            boolean visit(Integer element) {
                System.out.println(element);
                return false;
            }
        });
    }

    static  void treeSetTest(){
        Set<Integer> listSet = new TreeSet<>();
        listSet.add(10);
        listSet.add(11);
        listSet.add(12);
        listSet.add(13);
        listSet.add(10);
        listSet.traversal(new Set.Visitor<Integer>() {
            @Override
            boolean visit(Integer element) {
                System.out.println(element);
                return false;
            }
        });
    }
}
