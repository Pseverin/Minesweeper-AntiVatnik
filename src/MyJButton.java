import javax.swing.*;

/**
 * Created by Пархоменко on 24.07.14.
 */

//усовершенствованная кнопка со счетчиком иконок
public class MyJButton extends JButton {
    private int icCount=0;

    public void setIcCount(int i)
    {
        this.icCount=i;
    }

    public int getIcCount()
    {
        return this.icCount;
    }
}
