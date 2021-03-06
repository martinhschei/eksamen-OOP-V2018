package Helpers;

import GamePieces.ChessPiece;
import Interfaces.*;

import java.awt.*;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.io.Serializable;

public class Field implements Serializable {
	
	private ChessPiece currentPiece;
	private JButton button;
	private final Position position;
	private boolean selected;
	private final transient IsMover mover;
	public static Field selectedField;
	private boolean isHighlighted;

	private static final String imgPath = System.getProperty("user.dir") + "\\img\\";

	private JLabel Image() {
		
	    BufferedImage img = null;
		try {
			String imgString = Field.imgPath + this.currentPiece.getImagePath();
			img = ImageIO.read(new File(imgString));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new JLabel(new ImageIcon(img));
	}

	public Field(Position position, IsMover mover)
	{
		this.position = position;
		this.currentPiece = null;
		this.selected = false;
		this.mover = mover;
		this.createFieldButton();
	}
	
	private boolean isFieldWhite()
	{
		if (this.position.getRow() % 2 == 0) {
			return this.position.getColumn() == 'a' || this.position.getColumn() == 'c' || this.position.getColumn() == 'e' || this.position.getColumn() == 'g';
		} else {
			return this.position.getColumn() == 'b' || this.position.getColumn() == 'd' || this.position.getColumn() == 'f' || this.position.getColumn() == 'h';
		}
	}

	public void highlight()
    {
        this.button.setBackground(Color.ORANGE);
        this.isHighlighted = true;
    }

    public boolean isFieldHighlighted()
    {
        return this.isHighlighted;
    }

    public void clearHighlights()
    {
        this.isHighlighted = false;
        this.reset();
    }

	private void clearSelection()
    {
		this.button.removeAll();
       	this.reset();
    }

	private void createFieldButton()
	{
		this.button = new JButton();
		if (this.currentPiece != null) {
			this.button.add(this.Image());
		}
		this.button.setBorderPainted(false);
		this.button.setFocusPainted(false);
		this.button.setContentAreaFilled(true);
		
		if (this.isFieldWhite()) {
			this.button.setBackground(Color.WHITE);
			this.button.setForeground(Color.GRAY);
		}
		else {
			this.button.setBackground(Color.GRAY);
			this.button.setForeground(Color.WHITE);
		}
		this.button.addActionListener(e -> this.fieldClick() );
    }

    private void handleClickNotAllowed()
	{
		System.out.println("Click not allowed");
	}

	public void reset()
	{
		if(!this.isHighlighted)
		{
			if (this.isFieldWhite()) {
				this.button.setBackground(Color.white);
			} else {
				this.button.setBackground(Color.gray);
			}
		}
		else{
			this.button.setBackground(Color.ORANGE);
		}

	}

	private void fieldClick()
	{
		if (!this.mover.clickAllowed(this)) {
			this.handleClickNotAllowed();
			return;
		}

		// deselect
		if (Field.selectedField == this) {
			this.selected = false;
			this.reset();
			Field.selectedField = null;
			return;
		}

		// select
		if (!this.selected && this.currentPiece != null && Field.selectedField == null) {
			this.button.setBackground(Color.green);
			this.button.repaint();
			this.selected = true;
			Field.selectedField = this;
			return;
		}

		// move
		if (!this.selected && Field.selectedField != null && Field.selectedField != this) {
			this.mover.movePiece(Field.selectedField.currentPiece, Field.selectedField, this, false);
			this.selected = false;
			Field.selectedField.selected = false;
			Field.selectedField = null;
        }
	}
	
	public boolean isCurrentPieceWhite()
	{
		if (this.currentPiece != null) {
			return this.currentPiece.isWhite();
		}
		return false;
	}

	public boolean isAtPosition(Position pos)
	{
		return (this.position.getRow() == pos.getRow() && this.position.getColumn() == pos.getColumn());
	}
	
	public void setPiece(ChessPiece piece)
	{
		this.currentPiece = piece;
		if (piece == null) {
			this.clearSelection();
        } else {
    		this.button.add(Image());
		}
	}

	public String getCurrentPieceName()
	{
		if (this.hasPiece()) {
			return this.currentPiece.getName();
		}
		return null;
	}

	public boolean hasPiece() {
		return this.currentPiece != null;
	}

	public int getRow()
	{
		return this.position.getRow();
	}

	public char getColumn()
	{
		return this.position.getColumn();
	}
	
	public JButton getButton()
	{
		return this.button;
	}

	public ChessPiece getCurrentPiece() {
		return currentPiece;
	}

    public String toString() {
        return this.getColumn() + Integer.toString(this.getRow());
    }
}