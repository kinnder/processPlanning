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

import planning.model.Link;
import planning.model.System;

public class LinksDataModelTest {

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

	LinksDataModel testable;

	@BeforeEach
	public void setup() {
		editorDataModel_mock = context.mock(EditorDataModel.class);

		testable = new LinksDataModel(editorDataModel_mock);
	}

	@Test
	public void newInstance() {
		assertEquals(0, testable.getRowCount());
	}

	@Test
	public void loadLinks() {
		final System selectedSystem_mock = context.mock(System.class);
		final DefaultMutableTreeNode selectedNode_mock = context.mock(DefaultMutableTreeNode.class);
		final List<Link> links = new ArrayList<Link>();
		final Link link_1_mock = context.mock(Link.class, "link-1");
		final Link link_2_mock = context.mock(Link.class, "link-2");
		links.add(link_1_mock);
		links.add(link_2_mock);

		context.checking(new Expectations() {
			{
				oneOf(selectedSystem_mock).getLinks();
				will(returnValue(links));
			}
		});

		testable.loadLinks(selectedSystem_mock, selectedNode_mock);
		assertEquals(2, testable.getRowCount());
	}

	@Test
	public void getColumnClass() {
		assertEquals(String.class, testable.getColumnClass(LinksDataModel.COLUMN_IDX_NAME));
		assertEquals(String.class, testable.getColumnClass(LinksDataModel.COLUMN_IDX_ID1));
		assertEquals(String.class, testable.getColumnClass(LinksDataModel.COLUMN_IDX_ID2));
	}

	@Test
	public void getColumnName() {
		assertEquals("name", testable.getColumnName(LinksDataModel.COLUMN_IDX_NAME));
		assertEquals("id1", testable.getColumnName(LinksDataModel.COLUMN_IDX_ID1));
		assertEquals("id2", testable.getColumnName(LinksDataModel.COLUMN_IDX_ID2));
	}

	@Test
	public void insertLink() {
		final System selectedSystem_mock = context.mock(System.class);
		final DefaultMutableTreeNode selectedNode_mock = context.mock(DefaultMutableTreeNode.class);
		final List<Link> links = new ArrayList<Link>();

		context.checking(new Expectations() {
			{
				oneOf(selectedSystem_mock).getLinks();
				will(returnValue(links));
			}
		});
		testable.loadLinks(selectedSystem_mock, selectedNode_mock);

		context.checking(new Expectations() {
			{
				oneOf(selectedSystem_mock).addLink(with(any(Link.class)));

				oneOf(editorDataModel_mock).nodeChanged(selectedNode_mock);
			}
		});

		testable.insertLink();
		assertEquals(1, testable.getRowCount());
	}

	@Test
	public void deleteLink_no_row_selected() {
		testable.deleteLink(-1);
	}

	@Test
	public void deleteLink() {
		final System selectedSystem_mock = context.mock(System.class);
		final DefaultMutableTreeNode selectedNode_mock = context.mock(DefaultMutableTreeNode.class);
		final List<Link> links = new ArrayList<Link>();
		final Link link_1_mock = context.mock(Link.class, "link-1");
		final Link link_2_mock = context.mock(Link.class, "link-2");
		links.add(link_1_mock);
		links.add(link_2_mock);

		context.checking(new Expectations() {
			{
				oneOf(selectedSystem_mock).getLinks();
				will(returnValue(links));
			}
		});
		testable.loadLinks(selectedSystem_mock, selectedNode_mock);

		context.checking(new Expectations() {
			{
				oneOf(selectedSystem_mock).removeLink(link_1_mock);

				oneOf(editorDataModel_mock).nodeChanged(selectedNode_mock);
			}
		});

		testable.deleteLink(0);
		assertEquals(1, testable.getRowCount());
	}

	@Test
	public void setValueAt_name() {
		final System selectedSystem_mock = context.mock(System.class);
		final DefaultMutableTreeNode selectedNode_mock = context.mock(DefaultMutableTreeNode.class);
		final List<Link> links = new ArrayList<Link>();
		final Link link_1_mock = context.mock(Link.class, "link-1");
		final Link link_2_mock = context.mock(Link.class, "link-2");
		links.add(link_1_mock);
		links.add(link_2_mock);

		context.checking(new Expectations() {
			{
				oneOf(selectedSystem_mock).getLinks();
				will(returnValue(links));
			}
		});
		testable.loadLinks(selectedSystem_mock, selectedNode_mock);

		context.checking(new Expectations() {
			{
				oneOf(link_1_mock).setName("value");

				oneOf(editorDataModel_mock).nodeChanged(selectedNode_mock);
			}
		});

		testable.setValueAt("value", 0, LinksDataModel.COLUMN_IDX_NAME);
	}

	@Test
	public void setValueAt_id1() {
		final System selectedSystem_mock = context.mock(System.class);
		final DefaultMutableTreeNode selectedNode_mock = context.mock(DefaultMutableTreeNode.class);
		final List<Link> links = new ArrayList<Link>();
		final Link link_1_mock = context.mock(Link.class, "link-1");
		final Link link_2_mock = context.mock(Link.class, "link-2");
		links.add(link_1_mock);
		links.add(link_2_mock);

		context.checking(new Expectations() {
			{
				oneOf(selectedSystem_mock).getLinks();
				will(returnValue(links));
			}
		});
		testable.loadLinks(selectedSystem_mock, selectedNode_mock);

		context.checking(new Expectations() {
			{
				oneOf(link_1_mock).setId1("value");

				oneOf(editorDataModel_mock).nodeChanged(selectedNode_mock);
			}
		});

		testable.setValueAt("value", 0, LinksDataModel.COLUMN_IDX_ID1);
	}

	@Test
	public void setValueAt_id2() {
		final System selectedSystem_mock = context.mock(System.class);
		final DefaultMutableTreeNode selectedNode_mock = context.mock(DefaultMutableTreeNode.class);
		final List<Link> links = new ArrayList<Link>();
		final Link link_1_mock = context.mock(Link.class, "link-1");
		final Link link_2_mock = context.mock(Link.class, "link-2");
		links.add(link_1_mock);
		links.add(link_2_mock);

		context.checking(new Expectations() {
			{
				oneOf(selectedSystem_mock).getLinks();
				will(returnValue(links));
			}
		});
		testable.loadLinks(selectedSystem_mock, selectedNode_mock);

		context.checking(new Expectations() {
			{
				oneOf(link_1_mock).setId2("value");

				oneOf(editorDataModel_mock).nodeChanged(selectedNode_mock);
			}
		});

		testable.setValueAt("value", 0, LinksDataModel.COLUMN_IDX_ID2);
	}

	@Test
	public void setValueAt() {
		final System selectedSystem_mock = context.mock(System.class);
		final DefaultMutableTreeNode selectedNode_mock = context.mock(DefaultMutableTreeNode.class);
		final List<Link> links = new ArrayList<Link>();
		final Link link_1_mock = context.mock(Link.class, "link-1");
		final Link link_2_mock = context.mock(Link.class, "link-2");
		links.add(link_1_mock);
		links.add(link_2_mock);

		context.checking(new Expectations() {
			{
				oneOf(selectedSystem_mock).getLinks();
				will(returnValue(links));
			}
		});
		testable.loadLinks(selectedSystem_mock, selectedNode_mock);

		context.checking(new Expectations() {
			{
				oneOf(editorDataModel_mock).nodeChanged(selectedNode_mock);
			}
		});

		testable.setValueAt("value", 0, -1);
	}

	@Test
	public void getValueAt_name() {
		final System selectedSystem_mock = context.mock(System.class);
		final DefaultMutableTreeNode selectedNode_mock = context.mock(DefaultMutableTreeNode.class);
		final List<Link> links = new ArrayList<Link>();
		final Link link_1_mock = context.mock(Link.class, "link-1");
		final Link link_2_mock = context.mock(Link.class, "link-2");
		links.add(link_1_mock);
		links.add(link_2_mock);

		context.checking(new Expectations() {
			{
				oneOf(selectedSystem_mock).getLinks();
				will(returnValue(links));
			}
		});
		testable.loadLinks(selectedSystem_mock, selectedNode_mock);

		context.checking(new Expectations() {
			{
				oneOf(link_1_mock).getName();
				will(returnValue("value"));
			}
		});

		assertEquals("value", testable.getValueAt(0, LinksDataModel.COLUMN_IDX_NAME));
	}

	@Test
	public void getValueAt_id1() {
		final System selectedSystem_mock = context.mock(System.class);
		final DefaultMutableTreeNode selectedNode_mock = context.mock(DefaultMutableTreeNode.class);
		final List<Link> links = new ArrayList<Link>();
		final Link link_1_mock = context.mock(Link.class, "link-1");
		final Link link_2_mock = context.mock(Link.class, "link-2");
		links.add(link_1_mock);
		links.add(link_2_mock);

		context.checking(new Expectations() {
			{
				oneOf(selectedSystem_mock).getLinks();
				will(returnValue(links));
			}
		});
		testable.loadLinks(selectedSystem_mock, selectedNode_mock);

		context.checking(new Expectations() {
			{
				oneOf(link_1_mock).getId1();
				will(returnValue("value"));
			}
		});

		assertEquals("value", testable.getValueAt(0, LinksDataModel.COLUMN_IDX_ID1));
	}

	@Test
	public void getValueAt_id2() {
		final System selectedSystem_mock = context.mock(System.class);
		final DefaultMutableTreeNode selectedNode_mock = context.mock(DefaultMutableTreeNode.class);
		final List<Link> links = new ArrayList<Link>();
		final Link link_1_mock = context.mock(Link.class, "link-1");
		final Link link_2_mock = context.mock(Link.class, "link-2");
		links.add(link_1_mock);
		links.add(link_2_mock);

		context.checking(new Expectations() {
			{
				oneOf(selectedSystem_mock).getLinks();
				will(returnValue(links));
			}
		});
		testable.loadLinks(selectedSystem_mock, selectedNode_mock);

		context.checking(new Expectations() {
			{
				oneOf(link_1_mock).getId2();
				will(returnValue("value"));
			}
		});

		assertEquals("value", testable.getValueAt(0, LinksDataModel.COLUMN_IDX_ID2));
	}

	@Test
	public void getValueAt() {
		final System selectedSystem_mock = context.mock(System.class);
		final DefaultMutableTreeNode selectedNode_mock = context.mock(DefaultMutableTreeNode.class);
		final List<Link> links = new ArrayList<Link>();
		final Link link_1_mock = context.mock(Link.class, "link-1");
		final Link link_2_mock = context.mock(Link.class, "link-2");
		links.add(link_1_mock);
		links.add(link_2_mock);

		context.checking(new Expectations() {
			{
				oneOf(selectedSystem_mock).getLinks();
				will(returnValue(links));
			}
		});
		testable.loadLinks(selectedSystem_mock, selectedNode_mock);

		assertEquals(null, testable.getValueAt(0, -1));
	}

	@Test
	public void clear() {
		testable.clear();
	}
}
