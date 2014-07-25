

import java.awt.*;
import java.awt.event.*;
import java.net.URL;
import java.util.HashSet;
import java.util.Random;
import javax.swing.*;


public class AntiVatnik extends MouseAdapter  {

    //компоненты GUI
    private JFrame frame;
    private JPanel mainPanel, mPanel, wPanel;


    private JLabel[] labMatrix;
    private MyJButton[][] butMatrix;
    private String [][] bombMatrix;

    private int bombs;

    private int x;
    private int y;


    private int bombCounter;

    private Icon icMark;
    private Icon icVopros;
    private Icon icVatnikWin;
    private Icon icVatnikLoose;

    private HashSet<Integer> set;
    private HashSet<Integer> winNumbers;


    //конструктор
    public AntiVatnik(int xx, int yy, int bom)
    {
        bombs = bom;
        x = xx;
        y = yy;
        bombCounter=bombs;

        set = new HashSet<Integer>();

        winNumbers = new HashSet<Integer>();
        //контейнеры


        frame = new JFrame("Сапер - АнтіВатнік");

        //по закрытию формы - закрывать приложение
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        mainPanel = new JPanel(new BorderLayout());
        mPanel = new JPanel();
        wPanel = new JPanel();

        mPanel.setLayout(new GridLayout(1, 5));
        wPanel.setLayout(new GridLayout(x, y));


        mainPanel.add(mPanel, BorderLayout.NORTH);
        mainPanel.add(wPanel, BorderLayout.CENTER);


        //добавить главную панель на фрейм
        frame.getContentPane().add(mainPanel);

        //добавить кнопки и текстовые поля на панели
        addComponents();
        createMap();

        //установить необходимый размер фрейма и компонентов

        frame.setSize(x*55, y*55);
        //отобразить фрейм - главное окно программы
        frame.setVisible(true);


        frame.setResizable(false);

        URL imgURL = AntiVatnik.class.getResource("images/mark.png");
        icMark= new ImageIcon(imgURL);

        imgURL = AntiVatnik.class.getResource("images/vopros.png");
        icVopros= new ImageIcon(imgURL);

        imgURL = AntiVatnik.class.getResource("images/vatnik_win.png");
        icVatnikWin= new ImageIcon(imgURL);

        imgURL = AntiVatnik.class.getResource("images/vatnik_loose.png");
        icVatnikLoose= new ImageIcon(imgURL);

        frame.setLocationRelativeTo(null);

    }

    // добавление всех необходимых компонентов
    private void addComponents() {


        labMatrix = new JLabel[5];

        butMatrix= new MyJButton[x][y];
        bombMatrix = new String[x][y];

        for(int j=0; j<5; j++)
            labMatrix[j]=new JLabel();

        labMatrix[0].setText("Поле:");
        labMatrix[1].setText(x+"x"+y);
        labMatrix[2].setText("Час:");
        labMatrix[3].setText("0");
        labMatrix[4].setText(bombCounter+ " бомб");


        for (int i=0; i<x; i++)
            for(int j=0; j<y; j++)
                butMatrix[i][j]=new MyJButton();

        setBombMatrix();


        //выравнивание

        labMatrix[0].setHorizontalAlignment(JLabel.CENTER);
        labMatrix[2].setHorizontalAlignment(JLabel.CENTER);

        //добавление компонентов на панель

        for(int j=0; j<5; j++)
            mPanel.add(labMatrix[j]);

        for (int i=0; i<x; i++)
            for(int j=0; j<y; j++)
                wPanel.add(butMatrix[i][j]);



        //слушатели событий
        for (int i=0; i<x; i++)
            for(int j=0; j<y; j++)
                butMatrix[i][j].addMouseListener(this);




    }

    //нажатие кнопки мыши
    @Override
    public void mousePressed(MouseEvent e){

        if (SwingUtilities.isRightMouseButton(e))
        {
            rightClick((MyJButton)e.getSource());

        }
        if (SwingUtilities.isLeftMouseButton(e)) leftClick((MyJButton) e.getSource());
        labMatrix[4].setText(bombCounter+ " бомб");
        if(isWin()) win();
    }

    //обработка нажатия правой кнопки мыши
    public void rightClick(MyJButton but)
    {

        for (int i=0; i<x; i++)
            for(int j=0; j<y; j++)
                if (butMatrix[i][j].equals(but))
                     if (butMatrix[i][j].getText().equals(""))
                        if (butMatrix[i][j].getIcCount()==0)
                        {

                            butMatrix[i][j].setIcon(icMark);
                            butMatrix[i][j].setIcCount(1);
                            bombCounter--;
                            winNumbers.add(i*x+j+1);
                        }
                        else if (butMatrix[i][j].getIcCount()==1)
                        {
                            butMatrix[i][j].setIcon(icVopros);
                            butMatrix[i][j].setIcCount(2);
                            bombCounter++;
                            winNumbers.remove(i*x+j+1);

                        }
                        else if (butMatrix[i][j].getIcCount()==2) {butMatrix[i][j].setIcon(null); butMatrix[i][j].setIcCount(0);}

    }

    //обработка нажатия левой кнопки мыши
    public void leftClick(MyJButton but)
    {

        for (int i=0; i<x; i++)
            for(int j=0; j<y; j++)
                if (butMatrix[i][j].equals(but))
                    if (butMatrix[i][j].getText().equals(""))
                        if (bombMatrix[i][j].equals(" ")) otkritiePoley(i, j);
                        else if (!bombMatrix[i][j].equals("X"))
                        {
                            if (butMatrix[i][j].getIcCount()==1) bombCounter++;
                            butMatrix[i][j].setIcon(null);
                            butMatrix[i][j].setText(bombMatrix[i][j]);

                            if (i<(x/2)) butMatrix[i][j].setBackground(new Color(65, 105, 225)); else butMatrix[i][j].setBackground(Color.YELLOW);

                        }
                            else GameOver();

    }

    // автоматическое открытие полей при нажатии на пустую клетку
    public void otkritiePoley(int i, int j)
    {
        poleOtkr(i - 1, j - 1);
        poleOtkr(i - 1, j);
        poleOtkr(i - 1, j + 1);

        poleOtkr(i, j - 1);
        poleOtkr(i, j);
        poleOtkr(i, j + 1);

        poleOtkr(i + 1, j - 1);
        poleOtkr(i + 1, j);
        poleOtkr(i + 1, j + 1);
    }

    // открытие ячейки
    public void poleOtkr(int k, int f)
    {
        if ((k>=0)&&(f>=0)&&(k<x)&&(f<y))
            if (!butMatrix[k][f].getBackground().equals(Color.YELLOW)&&!butMatrix[k][f].getBackground().equals(new Color(65, 105, 225))
                    &&bombMatrix[k][f].equals(" "))
            {
                if (butMatrix[k][f].getIcCount()!=0) {butMatrix[k][f].setIcon(null); bombCounter++;}
                if (k<(x/2)) butMatrix[k][f].setBackground(new Color(65, 105, 225)); else butMatrix[k][f].setBackground(Color.YELLOW);
                if (bombMatrix[k][f].equals(" ")) otkritiePoley(k, f);
            }
        else
            {
                butMatrix[k][f].setText(bombMatrix[k][f]);
                if (k<(x/2)) butMatrix[k][f].setBackground(new Color(65, 105, 225)); else butMatrix[k][f].setBackground(Color.YELLOW);
            }

    }


    //обработка проигрыша
    public void GameOver ()
    {
        for (int i=0; i<x; i++)
            for (int j=0; j<y; j++)
                if(set.contains(i*x+j+1)) butMatrix[i][j].setIcon(icVatnikWin);

        JOptionPane.showMessageDialog(frame, "Нажаль, ватнікі перемогли :( Ми програли бій, але не програли війну ;)", "Повідомлення",JOptionPane.INFORMATION_MESSAGE);
        System.exit(0);
    }
    //проверка, не выиграли ли Вы?
    public boolean isWin ()
    {
                    return bombCounter==0&&winNumbers.containsAll(set);

    }
    //обработка выигрыша
    public void win ()
    {
        for (int i=0; i<x; i++)
            for (int j=0; j<y; j++)
                if(set.contains(i*x+j+1)) butMatrix[i][j].setIcon(icVatnikLoose);

        JOptionPane.showMessageDialog(frame, "Перемога!!! Ватнікам-сепаратістам глина!", "Повідомлення",JOptionPane.INFORMATION_MESSAGE);
        System.exit(0);
    }

    //генерирование матрицы бомб
    public void setBombMatrix()
    {
        Random rnd = new Random();

        while (set.size()!=bombs)
        {
            set.add(rnd.nextInt(x*y)+1);
        }


       //System.out.println(set.toString()); //отображение списка бомб в консоли

        for (int i=0; i<x; i++)
            for (int j=0; j<y; j++)
                if(set.contains(i*x+j+1))  bombMatrix[i][j]="X";
               else bombMatrix[i][j]="0";


    }

    // создание игрового поля (бомбы, числа)
    public void createMap()
    {
        for (int i=0; i<x; i++)
        {
            for (int j=0; j<y; j++)
                if (bombMatrix[i][j].equals("X"))
                    obrabotkaPoley(i, j);
        }

        for (int i=0; i<x; i++)
        {
            for (int j=0; j<y; j++)
                if (bombMatrix[i][j].equals("0"))
                    bombMatrix[i][j]=" ";
        }

        for (int i=0; i<x; i++)
        {
            for (int j=0; j<y; j++)
                System.out.print(bombMatrix[i][j]);
            System.out.println("");
        }

    }

    //обработка числовых полей
    public void obrabotkaPoley(int i, int j)
    {
        polePlus1(i-1,j-1);
        polePlus1(i-1,j);
        polePlus1(i-1,j+1);

        polePlus1(i,j-1);
        polePlus1(i,j+1);

        polePlus1(i+1,j-1);
        polePlus1(i+1,j);
        polePlus1(i+1,j+1);



    }
//увеличение числа поля на 1
    public void polePlus1(int k, int f)
    {
        if ((k>=0)&&(f>=0)&&(k<x)&&(f<y))
            if (!bombMatrix[k][f].equals("X"))
                bombMatrix[k][f]=Integer.toString(Integer.parseInt(bombMatrix[k][f])+1);
    }

}