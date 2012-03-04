/*
 * Copyright 2011 Gaurav Saxena < gsaxena81 AT gmail.com >
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.vk.gwt.designer.client.designer;

import java.util.Stack;

import com.google.gwt.user.client.Command;

public class UndoHelper implements IUndoHelper{
	
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
	
	public UndoHelper(){}
	
	public static UndoHelper getInstance(){
		return undoHelper;
	}
	@Override
	public boolean isUndoStackEmpty(){
		return undoStack.isEmpty();
	}
	@Override
	public boolean isRedoStackEmpty(){
		return redoStack.isEmpty();
	}
	@Override
	public void doCommand(Command redoCommand, Command undoCommand) {
		redoCommand.execute();
		undoStack.push(new UndoAbleCommand(redoCommand, undoCommand));
		redoStack.clear();
	}
	@Override
	public void undo(){
		if(!undoStack.isEmpty()) {
			UndoAbleCommand command = undoStack.pop();
			command.undoCommand.execute();
			redoStack.push(command);
		}
	}
	@Override
	public void redo(){
		if(!redoStack.isEmpty()) {
			UndoAbleCommand command = redoStack.pop();
			command.redoCommand.execute();
			undoStack.push(command);
		}
	}
	@Override
	public void clear() {
		undoStack.clear();
		redoStack.clear();
	}
}
