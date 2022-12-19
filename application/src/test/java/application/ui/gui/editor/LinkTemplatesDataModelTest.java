package application.ui.gui.editor;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import javax.swing.tree.DefaultMutableTreeNode;

import org.jmock.Expectations;
import org.jmock.imposters.ByteBuddyClassImposteriser;
import org.jmock.junit5.JUnit5Mockery;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

import planning.model.LinkTemplate;
import planning.model.SystemTemplate;

public class LinkTemplatesDataModelTest {

	@RegisterExtension
	JUnit5Mockery context = new JUnit5Mockery() {
		{
			setImposteriser(ByteBuddyClassImposteriser.INSTANCE);
		}
	};

	@AfterEach
	public void teardown() {
		context.assertIsSatisfied();
	}

	EditorDataModel editorDataModel_mock;

	LinkTemplatesDataModel testable;

	@BeforeEach
	public void setup() {
		editorDataModel_mock = context.mock(EditorDataModel.class);

		testable = new LinkTemplatesDataModel(editorDataModel_mock);
	}

	@Test
	public void newInstance() {
		assertEquals(0, testable.getRowCount());
	}

	@Test
	public void loadLinkTemplates() {
		final SystemTemplate selectedSystemTemplate_mock = context.mock(SystemTemplate.class);
		final DefaultMutableTreeNode selectedNode_mock = context.mock(DefaultMutableTreeNode.class);
		final List<LinkTemplate> linkTemplates = new ArrayList<LinkTemplate>();
		final LinkTemplate linkTemplate_1_mock = context.mock(LinkTemplate.class, "linkTemplate-1");
		final LinkTemplate LinkTemplate_2_mock = context.mock(LinkTemplate.class, "linkTemplate-2");
		linkTemplates.add(linkTemplate_1_mock);
		linkTemplates.add(LinkTemplate_2_mock);

		context.checking(new Expectations() {
			{
				oneOf(selectedSystemTemplate_mock).getLinkTemplates();
				will(returnValue(linkTemplates));
			}
		});

		testable.loadLinkTemplates(selectedSystemTemplate_mock, selectedNode_mock);
		assertEquals(2, testable.getRowCount());
	}

	@Test
	public void getColumnClass() {
		assertEquals(String.class, testable.getColumnClass(LinkTemplatesDataModel.COLUMN_IDX_NAME));
		assertEquals(String.class, testable.getColumnClass(LinkTemplatesDataModel.COLUMN_IDX_ID1));
		assertEquals(String.class, testable.getColumnClass(LinkTemplatesDataModel.COLUMN_IDX_ID2));
	}

	@Test
	public void getColumnName() {
		assertEquals("name", testable.getColumnName(LinkTemplatesDataModel.COLUMN_IDX_NAME));
		assertEquals("id1", testable.getColumnName(LinkTemplatesDataModel.COLUMN_IDX_ID1));
		assertEquals("id2", testable.getColumnName(LinkTemplatesDataModel.COLUMN_IDX_ID2));
	}

	@Test
	public void insertLinkTemplate() {
		final SystemTemplate selectedSystemTemplate_mock = context.mock(SystemTemplate.class);
		final DefaultMutableTreeNode selectedNode_mock = context.mock(DefaultMutableTreeNode.class);
		final List<LinkTemplate> linkTemplates = new ArrayList<LinkTemplate>();

		context.checking(new Expectations() {
			{
				oneOf(selectedSystemTemplate_mock).getLinkTemplates();
				will(returnValue(linkTemplates));
			}
		});
		testable.loadLinkTemplates(selectedSystemTemplate_mock, selectedNode_mock);

		context.checking(new Expectations() {
			{
				// TODO (2022-12-20 #73): добавить Matcher для LinkTemplate
				oneOf(selectedSystemTemplate_mock).addLinkTemplate(with(any(LinkTemplate.class)));

				oneOf(editorDataModel_mock).nodeChanged(selectedNode_mock);
			}
		});

		testable.insertLinkTemplate();
		assertEquals(1, testable.getRowCount());
	}

	@Test
	public void deleteLinkTemplate_no_row_selected() {
		testable.deleteLinkTemplate(-1);
	}

	@Test
	public void deleteLinkTemplate() {
		final SystemTemplate selectedSystemTemplate_mock = context.mock(SystemTemplate.class);
		final DefaultMutableTreeNode selectedNode_mock = context.mock(DefaultMutableTreeNode.class);
		final List<LinkTemplate> linkTemplates = new ArrayList<LinkTemplate>();
		final LinkTemplate linkTemplate_1_mock = context.mock(LinkTemplate.class, "linkTemplate-1");
		final LinkTemplate LinkTemplate_2_mock = context.mock(LinkTemplate.class, "linkTemplate-2");
		linkTemplates.add(linkTemplate_1_mock);
		linkTemplates.add(LinkTemplate_2_mock);

		context.checking(new Expectations() {
			{
				oneOf(selectedSystemTemplate_mock).getLinkTemplates();
				will(returnValue(linkTemplates));
			}
		});
		testable.loadLinkTemplates(selectedSystemTemplate_mock, selectedNode_mock);

		context.checking(new Expectations() {
			{
				oneOf(selectedSystemTemplate_mock).removeLinkTemplate(linkTemplate_1_mock);

				oneOf(editorDataModel_mock).nodeChanged(selectedNode_mock);
			}
		});

		testable.deleteLinkTemplate(0);
		assertEquals(1, testable.getRowCount());
	}

	@Test
	public void setValueAt_name() {
		final SystemTemplate selectedSystemTemplate_mock = context.mock(SystemTemplate.class);
		final DefaultMutableTreeNode selectedNode_mock = context.mock(DefaultMutableTreeNode.class);
		final List<LinkTemplate> linkTemplates = new ArrayList<LinkTemplate>();
		final LinkTemplate linkTemplate_1_mock = context.mock(LinkTemplate.class, "linkTemplate-1");
		final LinkTemplate LinkTemplate_2_mock = context.mock(LinkTemplate.class, "linkTemplate-2");
		linkTemplates.add(linkTemplate_1_mock);
		linkTemplates.add(LinkTemplate_2_mock);

		context.checking(new Expectations() {
			{
				oneOf(selectedSystemTemplate_mock).getLinkTemplates();
				will(returnValue(linkTemplates));
			}
		});
		testable.loadLinkTemplates(selectedSystemTemplate_mock, selectedNode_mock);

		context.checking(new Expectations() {
			{
				oneOf(linkTemplate_1_mock).setName("value");

				oneOf(editorDataModel_mock).nodeChanged(selectedNode_mock);
			}
		});

		testable.setValueAt("value", 0, LinkTemplatesDataModel.COLUMN_IDX_NAME);
	}

	@Test
	public void setValueAt_id1() {
		final SystemTemplate selectedSystemTemplate_mock = context.mock(SystemTemplate.class);
		final DefaultMutableTreeNode selectedNode_mock = context.mock(DefaultMutableTreeNode.class);
		final List<LinkTemplate> linkTemplates = new ArrayList<LinkTemplate>();
		final LinkTemplate linkTemplate_1_mock = context.mock(LinkTemplate.class, "linkTemplate-1");
		final LinkTemplate LinkTemplate_2_mock = context.mock(LinkTemplate.class, "linkTemplate-2");
		linkTemplates.add(linkTemplate_1_mock);
		linkTemplates.add(LinkTemplate_2_mock);

		context.checking(new Expectations() {
			{
				oneOf(selectedSystemTemplate_mock).getLinkTemplates();
				will(returnValue(linkTemplates));
			}
		});
		testable.loadLinkTemplates(selectedSystemTemplate_mock, selectedNode_mock);

		context.checking(new Expectations() {
			{
				oneOf(linkTemplate_1_mock).setId1("value");

				oneOf(editorDataModel_mock).nodeChanged(selectedNode_mock);
			}
		});

		testable.setValueAt("value", 0, LinkTemplatesDataModel.COLUMN_IDX_ID1);
	}

	@Test
	public void setValueAt_id2() {
		final SystemTemplate selectedSystemTemplate_mock = context.mock(SystemTemplate.class);
		final DefaultMutableTreeNode selectedNode_mock = context.mock(DefaultMutableTreeNode.class);
		final List<LinkTemplate> linkTemplates = new ArrayList<LinkTemplate>();
		final LinkTemplate linkTemplate_1_mock = context.mock(LinkTemplate.class, "linkTemplate-1");
		final LinkTemplate LinkTemplate_2_mock = context.mock(LinkTemplate.class, "linkTemplate-2");
		linkTemplates.add(linkTemplate_1_mock);
		linkTemplates.add(LinkTemplate_2_mock);

		context.checking(new Expectations() {
			{
				oneOf(selectedSystemTemplate_mock).getLinkTemplates();
				will(returnValue(linkTemplates));
			}
		});
		testable.loadLinkTemplates(selectedSystemTemplate_mock, selectedNode_mock);

		context.checking(new Expectations() {
			{
				oneOf(linkTemplate_1_mock).setId2("value");

				oneOf(editorDataModel_mock).nodeChanged(selectedNode_mock);
			}
		});

		testable.setValueAt("value", 0, LinkTemplatesDataModel.COLUMN_IDX_ID2);
	}

	@Test
	public void setValueAt() {
		final SystemTemplate selectedSystemTemplate_mock = context.mock(SystemTemplate.class);
		final DefaultMutableTreeNode selectedNode_mock = context.mock(DefaultMutableTreeNode.class);
		final List<LinkTemplate> linkTemplates = new ArrayList<LinkTemplate>();
		final LinkTemplate linkTemplate_1_mock = context.mock(LinkTemplate.class, "linkTemplate-1");
		final LinkTemplate LinkTemplate_2_mock = context.mock(LinkTemplate.class, "linkTemplate-2");
		linkTemplates.add(linkTemplate_1_mock);
		linkTemplates.add(LinkTemplate_2_mock);

		context.checking(new Expectations() {
			{
				oneOf(selectedSystemTemplate_mock).getLinkTemplates();
				will(returnValue(linkTemplates));
			}
		});
		testable.loadLinkTemplates(selectedSystemTemplate_mock, selectedNode_mock);

		context.checking(new Expectations() {
			{
				oneOf(editorDataModel_mock).nodeChanged(selectedNode_mock);
			}
		});

		testable.setValueAt("value", 0, -1);
	}

	@Test
	public void getValueAt_name() {
		final SystemTemplate selectedSystemTemplate_mock = context.mock(SystemTemplate.class);
		final DefaultMutableTreeNode selectedNode_mock = context.mock(DefaultMutableTreeNode.class);
		final List<LinkTemplate> linkTemplates = new ArrayList<LinkTemplate>();
		final LinkTemplate linkTemplate_1_mock = context.mock(LinkTemplate.class, "linkTemplate-1");
		final LinkTemplate LinkTemplate_2_mock = context.mock(LinkTemplate.class, "linkTemplate-2");
		linkTemplates.add(linkTemplate_1_mock);
		linkTemplates.add(LinkTemplate_2_mock);

		context.checking(new Expectations() {
			{
				oneOf(selectedSystemTemplate_mock).getLinkTemplates();
				will(returnValue(linkTemplates));
			}
		});
		testable.loadLinkTemplates(selectedSystemTemplate_mock, selectedNode_mock);

		context.checking(new Expectations() {
			{
				oneOf(linkTemplate_1_mock).getName();
				will(returnValue("value"));
			}
		});

		assertEquals("value", testable.getValueAt(0, LinkTemplatesDataModel.COLUMN_IDX_NAME));
	}

	@Test
	public void getValueAt_id1() {
		final SystemTemplate selectedSystemTemplate_mock = context.mock(SystemTemplate.class);
		final DefaultMutableTreeNode selectedNode_mock = context.mock(DefaultMutableTreeNode.class);
		final List<LinkTemplate> linkTemplates = new ArrayList<LinkTemplate>();
		final LinkTemplate linkTemplate_1_mock = context.mock(LinkTemplate.class, "linkTemplate-1");
		final LinkTemplate LinkTemplate_2_mock = context.mock(LinkTemplate.class, "linkTemplate-2");
		linkTemplates.add(linkTemplate_1_mock);
		linkTemplates.add(LinkTemplate_2_mock);

		context.checking(new Expectations() {
			{
				oneOf(selectedSystemTemplate_mock).getLinkTemplates();
				will(returnValue(linkTemplates));
			}
		});
		testable.loadLinkTemplates(selectedSystemTemplate_mock, selectedNode_mock);

		context.checking(new Expectations() {
			{
				oneOf(linkTemplate_1_mock).getId1();
				will(returnValue("value"));
			}
		});

		assertEquals("value", testable.getValueAt(0, LinkTemplatesDataModel.COLUMN_IDX_ID1));
	}

	@Test
	public void getValueAt_id2() {
		final SystemTemplate selectedSystemTemplate_mock = context.mock(SystemTemplate.class);
		final DefaultMutableTreeNode selectedNode_mock = context.mock(DefaultMutableTreeNode.class);
		final List<LinkTemplate> linkTemplates = new ArrayList<LinkTemplate>();
		final LinkTemplate linkTemplate_1_mock = context.mock(LinkTemplate.class, "linkTemplate-1");
		final LinkTemplate LinkTemplate_2_mock = context.mock(LinkTemplate.class, "linkTemplate-2");
		linkTemplates.add(linkTemplate_1_mock);
		linkTemplates.add(LinkTemplate_2_mock);

		context.checking(new Expectations() {
			{
				oneOf(selectedSystemTemplate_mock).getLinkTemplates();
				will(returnValue(linkTemplates));
			}
		});
		testable.loadLinkTemplates(selectedSystemTemplate_mock, selectedNode_mock);

		context.checking(new Expectations() {
			{
				oneOf(linkTemplate_1_mock).getId2();
				will(returnValue("value"));
			}
		});

		assertEquals("value", testable.getValueAt(0, LinkTemplatesDataModel.COLUMN_IDX_ID2));
	}

	@Test
	public void getValueAt() {
		final SystemTemplate selectedSystemTemplate_mock = context.mock(SystemTemplate.class);
		final DefaultMutableTreeNode selectedNode_mock = context.mock(DefaultMutableTreeNode.class);
		final List<LinkTemplate> linkTemplates = new ArrayList<LinkTemplate>();
		final LinkTemplate linkTemplate_1_mock = context.mock(LinkTemplate.class, "linkTemplate-1");
		final LinkTemplate LinkTemplate_2_mock = context.mock(LinkTemplate.class, "linkTemplate-2");
		linkTemplates.add(linkTemplate_1_mock);
		linkTemplates.add(LinkTemplate_2_mock);

		context.checking(new Expectations() {
			{
				oneOf(selectedSystemTemplate_mock).getLinkTemplates();
				will(returnValue(linkTemplates));
			}
		});
		testable.loadLinkTemplates(selectedSystemTemplate_mock, selectedNode_mock);

		assertEquals(null, testable.getValueAt(0, -1));
	}
}
