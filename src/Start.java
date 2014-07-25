/**
 * Created by Пархоменко on 24.07.14.
 */


import java.awt.event.*;
import javax.swing.*;


public class Start implements ActionListener{

    private JFrame frame;
    private JTextArea area;
    private JPanel mainPanel, mPanel, wPanel;

    private JTextField t1, t2, t3;


    private JLabel labMain,lab1, lab2;
    private MyJButton button;


    public Start()
    {

        //контейнеры


        frame = new JFrame("Сапер - АнтіВатнік");


        labMain = new JLabel();
        lab1 = new JLabel();
        lab2 = new JLabel();

        labMain.setText("Astalavista Separatista - гра для справжных патріотів!");
        lab1.setText("Розмір поля");
        lab2.setText("Кількість ватніків");

        t1 = new JTextField();
        t2 = new JTextField();
        t3 = new JTextField();

        t1.setText("10");
        t2.setText("10");
        t3.setText("10");

        button = new MyJButton();

        button.setText("Почати!");


        t1.setHorizontalAlignment(JTextField.CENTER);
        t2.setHorizontalAlignment(JTextField.CENTER);
        t3.setHorizontalAlignment(JTextField.CENTER);

        //по закрытию формы - закрывать приложение
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        mainPanel = new JPanel();
        mainPanel.setLayout(null);

        labMain.setBounds(30,10,350,30);

        lab1.setBounds(30,60,150,30);
        t1.setBounds(150,60,70,30);
        t2.setBounds(240,60,70,30);

        lab2.setBounds(30,100, 150, 30);
        t3.setBounds(190,100,70,30);
        button.setBounds(120,150,140,50);

        mainPanel.add(labMain);



        //добавить главную панель на фрейм
        frame.getContentPane().add(mainPanel);

        //добавить кнопки и текстовые поля на панели


        mainPanel.add(lab1);
        mainPanel.add(t1);
        mainPanel.add(t2);

        mainPanel.add(lab2);
        mainPanel.add(t3);
        mainPanel.add(button);



        frame.setSize(400, 250);


        frame.setVisible(true);

        button.addActionListener(this);

        frame.setLocationRelativeTo(null);

    }


    @Override
    public void actionPerformed(ActionEvent e)
    {
        int x = Integer.parseInt(t1.getText());
        int y = Integer.parseInt(t2.getText());
        int bom = Integer.parseInt(t3.getText());

        AntiVatnik a = new AntiVatnik(x, y, bom);

    }


    private static void setGUI() {
        //создать экземпляр класса Calculator
        Start gui = new Start();
    }

    //метод main - запуск программы происходит в этом методе
    public static void main(String[] args) {
        //создание компонентов в отдельном потоке
        javax.swing.SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                setGUI();
            }
        });
    }


}
