package com.vk.gwt.designer.client.designer;

import java.util.Stack;

import com.google.gwt.user.client.Command;

public class UndoHelper {
	
	private class UndoAbleCommand{
		private Command undoCommand;
		private Command redoCommand;
		
		public UndoAbleCommand(Command redoCommand, Command undoCommand) {
			this.undoCommand = undoCommand;
			this.redoCommand = redoCommand;
		}
	}
	
	private static UndoHelper undoHelper = new UndoHelper();
	
	@SuppressWarnings("serial")
	private Stack<UndoAbleCommand> undoStack = new Stack<UndoAbleCommand>(){
		@Override
		public UndoAbleCommand push(UndoAbleCommand c) {
			if(size() == 10)
				remove(9);
			UndoAbleCommand push = super.push(c);
			return push;
		}
		@Override
		public UndoAbleCommand pop() {
			if(size() > 0) {
				UndoAbleCommand pop = super.pop();
				return pop;
			}
			else return null;
		}
	};
	@SuppressWarnings("serial")
	private Stack<UndoAbleCommand> redoStack = new Stack<UndoAbleCommand>(){
		@Override
		public UndoAbleCommand push(UndoAbleCommand c) {
			if(size() == 10)
				remove(9);
			UndoAbleCommand push = super.push(c);
			return push;
		}
		@Override
		public UndoAbleCommand pop() {
			if(size() > 0) {
				UndoAbleCommand pop = super.pop();
				return pop;
			}
			else return null;
		}
	};
	
	private UndoHelper(){}
	
	public static UndoHelper getInstance(){
		return undoHelper;
	}
	
	public boolean isUndoStackEmpty(){
		return undoStack.isEmpty();
	}
	public boolean isRedoStackEmpty(){
		return redoStack.isEmpty();
	}
	public void doCommand(Command redoCommand, Command undoCommand) {
		redoCommand.execute();
		undoStack.push(new UndoAbleCommand(redoCommand, undoCommand));
		redoStack.clear();
	}
	public boolean undo(){
		if(!undoStack.isEmpty()) {
			UndoAbleCommand command = undoStack.pop();
			command.undoCommand.execute();
			redoStack.push(command);
		}
		return !undoStack.isEmpty();	
	}
	public boolean redo(){
		if(!redoStack.isEmpty()) {
			UndoAbleCommand command = redoStack.pop();
			command.redoCommand.execute();
			undoStack.push(command);
		}
		return !redoStack.isEmpty();
	}

	public void init() {
		undoStack.clear();
		redoStack.clear();
	}
}
