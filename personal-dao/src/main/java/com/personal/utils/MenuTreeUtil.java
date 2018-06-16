package com.personal.utils;

import com.personal.model.Tree;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 遍历获取数据层级
 * @author ycv
 * @description
 * @date 10:21 2017/12/29
 */
public class MenuTreeUtil {
    public static Map<String,Object> mapArray = new LinkedHashMap<String, Object>();
    public List<Tree> menuCommon;
    public List<Object> list = new ArrayList<Object>();

    public List<Object> menuList(List<Tree> menu,String key,String title){
        this.menuCommon = menu;
        for (Tree x : menu) {
            Map<String,Object> mapArr = new LinkedHashMap<String, Object>();
            if(x.getPid() == null){
                if (StringUtils.isNotBlank(x.getName())){
                    mapArr.put(key, x.getId());
                    mapArr.put(title, x.getName());
                    if (menuChild(x.getId(),key,title).size() > 0){
                        mapArr.put("children", menuChild(x.getId(),key,title));
                    }
                    list.add(mapArr);
                }
            }
        }
        return list;
    }

    public List<?> menuChild(String id,String key,String title){
        List<Object> lists = new ArrayList<Object>();
        for(Tree a:menuCommon){
            Map<String,Object> childArray = new LinkedHashMap<String, Object>();
            if(id.equals(a.getPid())){
                if (StringUtils.isNotBlank(a.getName())){
                    childArray.put(key, a.getId());
                    childArray.put(title, a.getName());
                    if (menuChild(a.getId(),key,title).size() > 0){
                        childArray.put("children", menuChild(a.getId(),key,title));
                    }
                    lists.add(childArray);
                }
            }
        }
        return lists;
    }
}
