package com.example.designpatterns.j2ee.transferobject;

import java.util.ArrayList;
import java.util.List;

/**
 * <ul>
 * <li>文件名称：StudentBO </li>
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
public class StudentBO {

    List<StudentVO> students;

    public StudentBO() {
        students = new ArrayList<StudentVO>();
        StudentVO student1 = new StudentVO("Robert",0);
        StudentVO student2 = new StudentVO("John",1);
        students.add(student1);
        students.add(student2);
    }

    public void deleteStudent(StudentVO student) {
        students.remove(student.getRollNo());
        System.out.println("Student: Roll No "
                + student.getRollNo() +", deleted from database");
    }

    //从数据库中检索学生名单
    public List<StudentVO> getAllStudents() {
        return students;
    }

    public StudentVO getStudent(int rollNo) {
        return students.get(rollNo);
    }

    public void updateStudent(StudentVO student) {
        students.get(student.getRollNo()).setName(student.getName());
        System.out.println("Student: Roll No "
                + student.getRollNo() +", updated in the database");
    }
}
