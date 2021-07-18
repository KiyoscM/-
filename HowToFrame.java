import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import java.io.*;

class HowToFrame extends JFrame implements ActionListener {
    private static final long serialVersionUID = 1L;
    ImagePanel single_part1;
    ImagePanel single_part2;
    ImagePanel single_part3;
    ImagePanel single_part4;

    ImagePanel multi_part1;
    ImagePanel multi_part2;
    ImagePanel multi_part3;
    ImagePanel multi_part4;

    public HowToFrame(String title, Rectangle rectangle) {
        super("遊び方");
        EventQueue.invokeLater(new Runnable() {

            @Override
            public void run() {
                initHowToUI(title, rectangle);
            }
        });

    }

    protected void initHowToUI(String title, Rectangle rectangle) {
        setTitle(title);
        setBounds((int) rectangle.getX(), (int) rectangle.getY(), (int) rectangle.getWidth(),
                (int) rectangle.getHeight());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        single_part1 = new ImagePanel("images/how_to_play_single_part1.png");
        single_part2 = new ImagePanel("images/how_to_play_single_part2.png");
        single_part3 = new ImagePanel("images/how_to_play_single_part3.png");
        single_part4 = new ImagePanel("images/how_to_play_single_part4.png");

        multi_part1 = new ImagePanel("images/how_to_play_multi_part1.png");
        multi_part2 = new ImagePanel("images/how_to_play_multi_part2.png");
        multi_part3 = new ImagePanel("images/how_to_play_multi_part3.png");
        multi_part4 = new ImagePanel("images/how_to_play_multi_part4.png");

        Font fontMessage = null;
        try {
            fontMessage = Font.createFont(Font.TRUETYPE_FONT, new File("fonts/AmeChanPopMaruTTFLight-Regular.ttf"))
                    .deriveFont(Font.CENTER_BASELINE, 25);
        } catch (Exception e) {
            e.printStackTrace();
        }

        ImagePanel backgroundPanel = new ImagePanel("images/background2.png");
        backgroundPanel.setLayout(new GridLayout(4, 1));

        VectorButton singleButton = new VectorButton();
        singleButton.setFont(fontMessage);
        singleButton.setText("シングルモード");
        singleButton.addActionListener(this);
        singleButton.setActionCommand("single");

        VectorButton vsButton = new VectorButton();
        vsButton.setFont(fontMessage);
        vsButton.setText("vsモード");
        vsButton.addActionListener(this);
        vsButton.setActionCommand("vs");

        VectorButton multiButton = new VectorButton();
        multiButton.setFont(fontMessage);
        multiButton.setText("バトルロワイアル");
        multiButton.addActionListener(this);
        multiButton.setActionCommand("multi");

        VectorButton closeButton = new VectorButton();
        closeButton.setFont(fontMessage);
        closeButton.setText("とじる");
        closeButton.addActionListener(this);
        closeButton.setActionCommand("close");

        backgroundPanel.add(singleButton);
        backgroundPanel.add(vsButton);
        backgroundPanel.add(multiButton);
        backgroundPanel.add(closeButton);

        add(backgroundPanel);
    }

    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        if (command.equals("close")) {
            dispose();
        } else if (command.equals("single")) {
            dispose();

            JFrame singleFrame = new JFrame("シングルモード");
            singleFrame.setBounds(100, 100, 600, 500);
            singleFrame.setBackground(new Color(123, 63, 0));

            JTabbedPane single = new VenetianPane();
            single.setBackground(Color.BLUE);
            single.setOpaque(false);

            VectorButton nextButton1 = new VectorButton();
            nextButton1.setText("つぎへ");
            nextButton1.setBorderPainted(false);
            nextButton1.addActionListener(e1 -> {
                single.setSelectedIndex(1);
            });
            single_part1.setOpaque(false);
            single_part1.setLayout(new BorderLayout());
            single_part1.add(nextButton1, BorderLayout.SOUTH);

            VectorButton nextButton2 = new VectorButton();
            nextButton2.setText("つぎへ");
            nextButton2.setBorderPainted(false);
            nextButton2.addActionListener(e1 -> {
                single.setSelectedIndex(2);
            });
            single_part2.setOpaque(false);
            single_part2.setLayout(new BorderLayout());
            single_part2.add(nextButton2, BorderLayout.SOUTH);

            VectorButton nextButton3 = new VectorButton();
            nextButton3.setText("つぎへ");
            nextButton3.setBorderPainted(false);
            nextButton3.addActionListener(e1 -> {
                single.setSelectedIndex(3);
            });
            single_part3.setOpaque(false);
            single_part3.setLayout(new BorderLayout());
            single_part3.add(nextButton3, BorderLayout.SOUTH);

            VectorButton nextButton4 = new VectorButton();
            nextButton4.setText("つぎへ");
            nextButton4.setBorderPainted(false);
            nextButton4.addActionListener(e1 -> {
                single.setSelectedIndex(4);
            });
            single_part4.setOpaque(false);
            single_part4.setLayout(new BorderLayout());
            single_part4.add(nextButton4, BorderLayout.SOUTH);

            single.addTab("①", single_part1);
            single.addTab("②", single_part2);
            single.addTab("③", single_part3);
            single.addTab("④", single_part4);

            singleFrame.getContentPane().add(single);
            // singleFrame.pack();
            singleFrame.setVisible(true);

        } else if (command.equals("vs")) {

            dispose();

            JFrame vsFrame = new JFrame("vsモード");
            vsFrame.setBounds(100, 100, 600, 500);
            JTabbedPane vs = new VenetianPane();
            vs.setBackground(Color.BLUE);
            vs.setOpaque(false);

            VectorButton nextButton1 = new VectorButton();
            nextButton1.setText("つぎへ");
            nextButton1.setBorderPainted(false);
            nextButton1.addActionListener(e1 -> {
                vs.setSelectedIndex(1);
            });
            single_part1.setOpaque(false);
            single_part1.setLayout(new BorderLayout());
            single_part1.add(nextButton1, BorderLayout.SOUTH);

            VectorButton nextButton2 = new VectorButton();
            nextButton2.setText("つぎへ");
            nextButton2.setBorderPainted(false);
            nextButton2.addActionListener(e1 -> {
                vs.setSelectedIndex(2);
            });
            single_part2.setOpaque(false);
            single_part2.setLayout(new BorderLayout());
            single_part2.add(nextButton2, BorderLayout.SOUTH);

            VectorButton nextButton3 = new VectorButton();
            nextButton3.setText("つぎへ");
            nextButton3.setBorderPainted(false);
            nextButton3.addActionListener(e1 -> {
                vs.setSelectedIndex(3);
            });
            single_part3.setOpaque(false);
            single_part3.setLayout(new BorderLayout());
            single_part3.add(nextButton3, BorderLayout.SOUTH);

            VectorButton nextButton4 = new VectorButton();
            nextButton4.setText("つぎへ");
            nextButton4.setBorderPainted(false);
            nextButton4.addActionListener(e1 -> {
                vs.setSelectedIndex(4);
            });
            single_part4.setOpaque(false);
            single_part4.setLayout(new BorderLayout());
            single_part4.add(nextButton4, BorderLayout.SOUTH);
            vs.addTab("①", single_part1);
            vs.addTab("②", single_part2);
            vs.addTab("③", single_part3);
            vs.addTab("④", single_part4);

            vsFrame.getContentPane().add(vs);
            // vsFrame.pack();
            vsFrame.setVisible(true);

        } else if (command.equals("multi")) {

            dispose();

            JFrame multiFrame = new JFrame("バトルロワイアル");
            multiFrame.setBounds(100, 100, 600, 500);
            JTabbedPane multi = new VenetianPane();
            multi.setBackground(Color.BLUE);
            multi.setOpaque(false);

            VectorButton nextButton1 = new VectorButton();
            nextButton1.setText("つぎへ");
            nextButton1.setBorderPainted(false);
            nextButton1.addActionListener(e1 -> {
                multi.setSelectedIndex(1);
            });
            multi_part1.setOpaque(false);
            multi_part1.setLayout(new BorderLayout());
            multi_part1.add(nextButton1, BorderLayout.SOUTH);

            VectorButton nextButton2 = new VectorButton();
            nextButton2.setText("つぎへ");
            nextButton2.setBorderPainted(false);
            nextButton2.addActionListener(e1 -> {
                multi.setSelectedIndex(2);
            });
            multi_part2.setOpaque(false);
            multi_part2.setLayout(new BorderLayout());
            multi_part2.add(nextButton2, BorderLayout.SOUTH);

            VectorButton nextButton3 = new VectorButton();
            nextButton3.setText("つぎへ");
            nextButton3.setBorderPainted(false);
            nextButton3.addActionListener(e1 -> {
                multi.setSelectedIndex(3);
            });
            multi_part3.setOpaque(false);
            multi_part3.setLayout(new BorderLayout());
            multi_part3.add(nextButton3, BorderLayout.SOUTH);

            VectorButton nextButton4 = new VectorButton();
            nextButton4.setText("つぎへ");
            nextButton4.setBorderPainted(false);
            nextButton4.addActionListener(e1 -> {
                multi.setSelectedIndex(4);
            });
            multi_part4.setOpaque(false);
            multi_part4.setLayout(new BorderLayout());
            multi_part4.add(nextButton4, BorderLayout.SOUTH);
            multi.addTab("①", multi_part1);
            multi.addTab("②", multi_part2);
            multi.addTab("③", multi_part3);
            multi.addTab("④", multi_part4);

            multiFrame.getContentPane().add(multi);
            // multiFrame.pack();
            multiFrame.setVisible(true);

        }
    }
}