package gui;

import java.util.Random;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.BorderFactory;

public class TicTacToe extends JFrame implements ActionListener {

	Font btnFont;
	Color bgColor, fgColor, winColor, panelColor;
	boolean isPlayerOne;

	String[] plays;
	JButton[] buttons;

	int XScore = 0;
	int OScore = 0;

	JLabel XLabel;
	JLabel OLabel;

	JLabel ScoreLabel1;
	JLabel ScoreLabel2;

	JLabel player;
	boolean[] turns = { true, false };

	private JButton createButton() {
		JButton btn = new JButton();
		btn.setText("");
		btn.setFont(btnFont);
		btn.setBackground(bgColor);
		btn.setForeground(fgColor);
		btn.addActionListener(this);
		btn.setFocusable(false);

		return btn;
	}

	private String acceptName(String text) {
		String res = JOptionPane.showInputDialog(null, text + "\nEnter your name");
		if (text.equalsIgnoreCase("player2") && XLabel.getText().split(" ")[0].equalsIgnoreCase(res)) {
			return acceptName(text);
		}
		return res == null || res.isBlank() ? acceptName(text) : res;
	}

	private JLabel createLabel(String text) {
		JLabel label = new JLabel(text);
		label.setFont(new Font("Ink Tree", Font.ITALIC, 55));
		return label;
	}

	public TicTacToe() {
		Random rd = new Random();

		btnFont = new Font("Ink Tree", Font.ITALIC, 55);
		isPlayerOne = turns[Math.round(rd.nextInt(2))];

		plays = new String[9];
		buttons = new JButton[9];

		bgColor = Color.black;
		fgColor = Color.yellow;
		winColor = Color.gray;
		panelColor = Color.black;

		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(3, 3));
		add(panel, BorderLayout.CENTER);

		for (int i = 0; i < 9; i++) {

			JButton btn = createButton();
			btn.setActionCommand(String.valueOf(i));
			panel.add(btn);

			buttons[i] = btn;
		}

		JPanel scorePanel = new JPanel();
		scorePanel.setBackground(panelColor);
		scorePanel.setLayout(new GridLayout(2, 1));
		add(scorePanel, BorderLayout.SOUTH);

		JPanel southPanelFirst = new JPanel();
		southPanelFirst.setLayout(new GridLayout(2, 2));
		southPanelFirst.setBackground(bgColor);

		JPanel southPanelSecond = new JPanel();
		southPanelSecond.setLayout(new GridLayout(1, 2));

		XLabel = createLabel(acceptName("player1"));
		XLabel.setHorizontalAlignment(JLabel.CENTER);
		OLabel = createLabel(acceptName("player2"));
		OLabel.setHorizontalAlignment(JLabel.CENTER);

		ScoreLabel1 = createLabel(String.valueOf(XScore));
		ScoreLabel2 = createLabel(String.valueOf(OScore));

		JButton resetButton = createButton();
		resetButton.setText("Reset");
		resetButton.setToolTipText("This resets the game");
		resetButton.setActionCommand("Reset");
		resetButton.setBorder(BorderFactory.createLineBorder(Color.red, 1));

		JButton exitButton = createButton();
		exitButton.setText("Exit");
		exitButton.setActionCommand("Exit");
		exitButton.setBorder(BorderFactory.createLineBorder(Color.red, 1));

		southPanelFirst.add(XLabel);
		southPanelFirst.add(ScoreLabel1);
		southPanelFirst.add(OLabel);
		southPanelFirst.add(ScoreLabel2);

		southPanelSecond.add(resetButton);
		southPanelSecond.add(exitButton);

		scorePanel.add(southPanelFirst);
		scorePanel.add(southPanelSecond);

		JPanel titlePanel = new JPanel();
		titlePanel.setBackground(panelColor);
		titlePanel.setLayout(new GridLayout(2, 1));
		add(titlePanel, BorderLayout.NORTH);

		JLabel title = new JLabel("Tic Tac Toe");
		titlePanel.add(title);
		title.setFont(btnFont);
		title.setHorizontalAlignment(JLabel.CENTER);
		title.setBackground(resetButton.getBackground());
		title.setForeground(resetButton.getForeground());

		player = new JLabel(isPlayerOne ? XLabel.getText() + "'s Turn" : OLabel.getText() + "'s Turn");
		titlePanel.add(player);
		player.setFont(btnFont);
		player.setHorizontalAlignment(JLabel.CENTER);
		player.setBackground(resetButton.getBackground());
		player.setForeground(resetButton.getForeground());

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setTitle("Tic Tac Toe");
		setVisible(true);
		setSize(750, 750);
		getContentPane().setBackground(Color.blue);
		setLocationRelativeTo(null);
		addWindowListener(new Windows());
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JButton btn = (JButton) e.getSource();
		if (e.getActionCommand().equals("Reset")) {
			int result = JOptionPane.showConfirmDialog(null, "Sure to restart?", "Confirmation Diolog",
					JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
			if (result == 0) {
				int result2 = JOptionPane.showConfirmDialog(null, "Do you want to change both players?",
						"Confirmation Dialog", JOptionPane.YES_NO_OPTION);
				if (result2 == 0) {
					reset();
					XScore = 0;
					ScoreLabel1.setText(String.valueOf(XScore));
					OScore = 0;
					ScoreLabel2.setText(String.valueOf(OScore));
					XLabel.setText(acceptName("player1"));
					OLabel.setText(acceptName("player2"));
					String turn = isPlayerOne ? XLabel.getText() + "'s Turn" : OLabel.getText() + "'s Turn";
					player.setText(turn);
				}

			}
		} else if (e.getActionCommand().equals("Exit"))
			System.exit(0);
		int index = Integer.valueOf(e.getActionCommand());

		if (btn.getText().equals("")) {
			String played;
			if (isPlayerOne) {
				btn.setForeground(Color.white);
				played = "X";
			} else {
				btn.setForeground(Color.red);
				played = "O";
			}

			btn.setText(played);
			plays[index] = played;

			isPlayerOne = !isPlayerOne;
			checkWin(played);

			boolean vacant = false;
			for (String str : plays) {
				if (str == null) {
					vacant = true;
				}
			}
			if (vacant == false) {
				JOptionPane.showMessageDialog(this, "No Winner\nPlay Again");
				reset();
			}

		}
	}

	public static void main(String[] args) {
		new TicTacToe();
	}

	void checkWin(String text) {

		int[][] winningPositions = { { 0, 1, 2 }, { 3, 4, 5 }, { 6, 7, 8 }, { 0, 4, 8 }, { 2, 4, 6 }, { 6, 3, 0 },
				{ 7, 4, 1 }, { 5, 8, 2 } };
		String turn = isPlayerOne ? XLabel.getText() + "'s Turn" : OLabel.getText() + "'s Turn";
		player.setText(turn);

		for (int[] arr : winningPositions) {

			int first = arr[0];
			int second = arr[1];
			int third = arr[2];

			if (plays[first] == null || plays[second] == null || plays[third] == null) {
				continue;
			}

			if (plays[first].equals(text) && plays[second].equals(text) && plays[third].equals(text)) {

				buttons[first].setBackground(winColor);
				buttons[second].setBackground(winColor);
				buttons[third].setBackground(winColor);

				if (text.equals("X")) {
					XScore++;
					ScoreLabel1.setText(String.valueOf(XScore));
					JOptionPane.showMessageDialog(this, XLabel.getText() + " wins");
				} else {
					OScore++;
					ScoreLabel2.setText(String.valueOf(OScore));
					JOptionPane.showMessageDialog(this, OLabel.getText() + " wins");
				}

				reset();
				break;
			}
		}
	}

	void reset() {
		for (int i = 0; i < buttons.length; i++) {
			plays[i] = null;
			buttons[i].setText("");
			buttons[i].setBackground(bgColor);
		}
	}

}
