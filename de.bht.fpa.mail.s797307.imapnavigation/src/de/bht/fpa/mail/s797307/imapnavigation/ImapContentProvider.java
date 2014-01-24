package de.bht.fpa.mail.s797307.imapnavigation;

import java.util.Collection;
import java.util.LinkedList;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;

import de.bht.fpa.mail.s000000.common.mail.model.Account;
import de.bht.fpa.mail.s000000.common.mail.model.Folder;

public class ImapContentProvider implements ITreeContentProvider {
  protected ImapNavigationView view;

  public ImapContentProvider(ImapNavigationView view) {
    this.view = view;
  }

  @Override
  public Object[] getChildren(Object parentElement) {
	 FolderNode folder = (FolderNode) parentElement;
	 return folder.getChildren();
  }

  @Override
  public boolean hasChildren(Object element) {
	  FolderNode folder = (FolderNode) element;
	  return folder.getChildren().length > 0;
  }

  @Override
  public Object[] getElements(Object parent) {
    return getChildren(parent);
  }

  @Override
  public void inputChanged(Viewer v, Object oldInput, Object newInput) {
  }

  @Override
  public void dispose() {
  }

  @Override
  public Object getParent(Object element) {
    return null;
  }
}