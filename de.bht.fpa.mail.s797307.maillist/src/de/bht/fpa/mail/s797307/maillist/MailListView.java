package de.bht.fpa.mail.s797307.maillist;

import java.text.SimpleDateFormat;
import java.util.Collection;

import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.ViewPart;

import de.bht.fpa.mail.s000000.common.mail.model.Message;
import de.bht.fpa.mail.s000000.common.mail.model.Recipient;
import de.bht.fpa.mail.s000000.common.mail.testdata.RandomTestDataProvider;
import de.ralfebert.rcputils.properties.BaseValue;
import de.ralfebert.rcputils.tables.ColumnBuilder;
import de.ralfebert.rcputils.tables.TableViewerBuilder;
import de.ralfebert.rcputils.tables.format.Formatter;
import de.ralfebert.rcputils.tables.format.StringValueFormatter;

public class MailListView extends ViewPart {
    private static final int IMPORTANCE_PERCENT_WIDTH = 70;
    private static final int RECEIVED_PERCENT_WIDTH = 140;
    private static final int READ_PERCENT_WIDTH = 40;
    private static final int SENDER_PERCENT_WIDTH = 160;
    private static final int RECIPIENTS_PERCENT_WIDTH = 160;
    private static final int SUBJECT_PERCENT_WIDTH = 250;
    private TableViewer tableViewer;

    @Override
    public void createPartControl(Composite parent) {
        TableViewerBuilder t = new TableViewerBuilder(parent);
        ColumnBuilder importance = t.createColumn("Importance");
        importance.setPixelWidth(IMPORTANCE_PERCENT_WIDTH);
        importance.bindToProperty("importance");
        importance.build();
        ColumnBuilder received = t.createColumn("Received");
        received.setPixelWidth(RECEIVED_PERCENT_WIDTH);
        received.useAsDefaultSortColumn();
        StringValueFormatter dateFormat = Formatter.forDate(SimpleDateFormat.getDateInstance(SimpleDateFormat.MEDIUM));
        received.format(dateFormat);
        received.bindToProperty("received");
        received.build();
        ColumnBuilder read = t.createColumn("Read");
        read.setPixelWidth(READ_PERCENT_WIDTH);
        read.bindToValue(new BaseValue<Message>() {
            @Override
            public Object get(Message message) {
                return message.isRead();
            }
        });
        read.build();
        ColumnBuilder sender = t.createColumn("Sender");
        sender.setPixelWidth(SENDER_PERCENT_WIDTH);
        sender.bindToProperty("sender.email");
        sender.build();
        ColumnBuilder recipients = t.createColumn("Recipients");
        recipients.setPixelWidth(RECIPIENTS_PERCENT_WIDTH);
        recipients.bindToValue(new BaseValue<Message>() {
            @Override
            public Object get(Message message) {
                StringBuilder sb = new StringBuilder();
                String prefix = ", ";
                for (Recipient recipient : message.getRecipients()) {
                    sb.append(", " + recipient.getEmail());
                }

                return sb.substring(prefix.length()).toString();
            }
        });
        recipients.build();
        ColumnBuilder subject = t.createColumn("Subject");
        subject.setPixelWidth(SUBJECT_PERCENT_WIDTH);
        subject.bindToProperty("subject");
        subject.build();
        Collection<Message> messages = new RandomTestDataProvider(50).getMessages();
        t.setInput(messages);
        tableViewer = t.getTableViewer();
        getSite().setSelectionProvider(tableViewer);
        getViewSite().getPage().addSelectionListener(new MaillistListener(this));
    }

    @Override
    public void setFocus() {

    }

}
