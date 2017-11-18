import jdk.jshell.spi.ExecutionControl;

import javax.swing.*;
import javax.swing.event.MouseInputAdapter;
import java.awt.*;
import java.awt.event.*;

class DragFrame extends JFrame {
    private JButton button;
    private JLabel statusLabel;
    private JPanel dragPanel;
    private JPanel statusPanel;
    private boolean isCaptured;

    public DragFrame() {
        super("Drag");
        setLayout(new BorderLayout());
        // Creating status panel
        statusPanel = new JPanel();
        statusPanel.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0)));

        statusLabel = new JLabel("X = ? , Y = ?");
        statusPanel.add(statusLabel);

        add(statusPanel, BorderLayout.SOUTH);

        // create work field
        button = new JButton("txt");
        button.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if (e.isControlDown() && isInWindow(e.getX() + button.getX(), e.getY() + button.getY())) {
                    button.setLocation(e.getX() + button.getX(), e.getY() + button.getY());
                }
                mouseMoved(e);
            }

            @Override
            public void mouseMoved(MouseEvent e) {
                StringBuilder strBuilder = new StringBuilder();
                statusLabel.setText(strBuilder.append("X = ").append(e.getX() + button.getX()).append(" , Y = ").
                        append(e.getY() + button.getY()).toString());
            }
        });

        dragPanel = new JPanel(null);
        dragPanel.add(button);
        button.setBounds(0, 0, 150, 25);
        add(dragPanel);

        dragPanel.addMouseListener(new MouseInputAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                button.setLocation(e.getX(), e.getY());
            }
        });

        dragPanel.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                mouseMoved(e);
            }

            @Override
            public void mouseMoved(MouseEvent e) {
                StringBuilder strBuilder = new StringBuilder();
                statusLabel.setText(strBuilder.append("X = ").append(e.getX()).append(" , Y = ").append(e.getY()).toString());
            }
        });
        dragPanel.setFocusable(true);
        dragPanel.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
                    if (button.getText().length() > 0) {
                        StringBuilder sb = new StringBuilder(button.getText());
                        button.setText(sb.deleteCharAt(sb.length() - 1).toString());
                    }

                } else {
                    Character c = e.getKeyChar();
                    if (Character.isDigit(c) || Character.isAlphabetic(c))
                        button.setText(button.getText() + c);
                }
            }
        });
    }

    private void updateCoordinates(int x, int y) {
        StringBuilder sb = new StringBuilder();
        sb.append("X = ").append(x).append(" , Y = ").append(y);
        this.statusLabel.setText(sb.toString());
    }

    private boolean isInWindow(int x, int y) {
        if (x + button.getWidth() <= dragPanel.getWidth() &&
                x >= 0 &&
                y >= 0 &&
                y + button.getHeight() <= dragPanel.getHeight())
            return true;
        return false;
    }
    // coordinates of mouse are local
    // add coords of left-upper corner

    // handlers :

    // MouseListener
    // mouseMotionListener
    // KeyListener
    // mouse events - is Control down
    public static void main(String[] args) {
        DragFrame frame = new DragFrame();
        frame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        frame.setLocation(300, 300);
        frame.setSize(300, 300);

        frame.setVisible(true);
    }
}
