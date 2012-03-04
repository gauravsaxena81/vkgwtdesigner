package com.vk.gwt.designer.client.designer;

import com.google.gwt.user.client.Command;

public interface IUndoHelper {

	boolean isUndoStackEmpty();

	boolean isRedoStackEmpty();

	void doCommand(Command redoCommand, Command undoCommand);

	void undo();

	void redo();

	void clear();

}
