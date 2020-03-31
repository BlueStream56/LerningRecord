package com.example.designpatterns.j2ee.mvc;

/**
 * <ul>
 * <li>文件名称：MVCPatternDemo </li>
 * <li>文件描述：暂无描述</li>
 * <li>版权所有：版权所有(C) 2016</li>
 * <li>公 司：厦门云顶伟业信息技术有限公司</li>
 * <li>内容摘要：</li>
 * <li>其他说明：</li>
 * <li>创建日期：2020/3/31 </li>
 * </ul>
 *
 * <ul>
 * <li>修改记录：</li>
 * <li>版 本 号：</li>
 * <li>修改日期：</li>
 * <li>修 改 人：</li>
 * <li>修改内容：</li>
 * </ul>
 *
 * @author caoxx
 * @version 1.0.0
 */
public class MVCPatternDemo {

    public static void main(String[] args) {

        //从数据库获取学生记录
        Student model  = retrieveStudentFromDatabase();

        //创建一个视图：把学生详细信息输出到控制台
        StudentView view = new StudentView();

        StudentController controller = new StudentController(model, view);

        controller.updateView();

        //更新模型数据
        controller.setStudentName("John");

        controller.updateView();

    }

    private static Student retrieveStudentFromDatabase(){
        Student student = new Student();
        student.setName("Robert");
        student.setRollNo("10");
        return student;
    }

}
