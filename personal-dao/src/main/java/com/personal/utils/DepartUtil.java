package com.personal.utils;

import com.personal.model.Tree;
import java.util.ArrayList;
import java.util.List;

/**
 * 递归获取某个父节点下的全部子节点
 * @author ycv
 * @description
 * @date 10:37 2018/1/8
 */
public class DepartUtil {
    /**
     * 子节点
     */
    private List childMenu = new ArrayList();

    public List childList(List<Tree> menuList,int pid){
        for (Tree t:menuList){
            //遍历和父节点相等的id
            if (Integer.valueOf(t.getPid()) == pid){
                //递归遍历下级
                childList(menuList,Integer.valueOf(t.getId()));
                childMenu.add(t.getId());
            }
        }
        return childMenu;
    }

}
