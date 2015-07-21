package com.zc.common.web.util;

import java.io.Serializable;
import java.util.Comparator;
import java.util.List;

import org.springframework.stereotype.Service;
import org.zkoss.lang.Strings;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Column;
import org.zkoss.zul.Columns;
import org.zkoss.zul.Grid;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listhead;
import org.zkoss.zul.Listheader;
import org.zkoss.zul.Paging;
import org.zkoss.zul.event.PagingEvent;
import org.zkoss.zul.event.ZulEvents;
import org.zkoss.zul.impl.XulElement;

import com.zc.common.model.BaseExample;
import com.zc.common.service.BaseService;
import com.zc.common.util.Constants;

@SuppressWarnings("unchecked")
@Service
public class PagingWrap implements Serializable {
	private static final long serialVersionUID = 6767116212570365151L;

	public void paginate(final BaseService baseService, final BaseExample example, final Paging paging, final XulElement xulElement, final int pageSize) {
		paging.addEventListener(ZulEvents.ON_PAGING, new OnPagingEventListener(baseService, example, paging, xulElement));
		paging.setPageSize(pageSize);
		paging.setDetailed(true);
		example.setStart(paging.getActivePage());
		example.setPageSize(paging.getPageSize());
		refreshData(baseService, example, paging, xulElement);
		initSortListener(baseService, example, paging, xulElement);
	}

	public void paginate(final BaseService baseService, final BaseExample example, final Paging paging, final XulElement xulElement) {
		paginate(baseService, example, paging, xulElement, paging.getPageSize());
	}

	private void refreshData(final BaseService baseService, final BaseExample example, final Paging paging, final XulElement xulElement) {
		paging.setTotalSize(baseService.getTotal(example));

		if (xulElement instanceof Listbox) {
			Listbox listBox = (Listbox) xulElement;
			listBox.setFixedLayout(true);
			listBox.setPaginal(paging);
			listBox.setModel(new ListModelList(baseService.paginate(example)));
		} else if (xulElement instanceof Grid) {
			Grid grid = (Grid) xulElement;
			grid.setFixedLayout(true);
			grid.setPaginal(paging);
			grid.setModel(new ListModelList(baseService.paginate(example)));
		}
	}

	private void initSortListener(final BaseService baseService, final BaseExample example, final Paging paging, final XulElement xulElement) {
		if (xulElement instanceof Listbox) {
			Listbox listBox = (Listbox) xulElement;
			Listhead listhead = listBox.getListhead();
			List<?> list = listhead.getChildren();
			OnSortEventListener onSortEventListener = new OnSortEventListener(baseService, example, paging, xulElement);
			for (Object object : list) {
				if (object instanceof Listheader) {
					Listheader lheader = (Listheader) object;
					if (lheader.getSortAscending() != null || lheader.getSortDescending() != null) {
						lheader.addEventListener(Events.ON_SORT, onSortEventListener);
					}
				}
			}
		} else if (xulElement instanceof Grid) {
			Grid grid = (Grid) xulElement;
			Columns columns = grid.getColumns();
			OnSortEventListener onSortEventListener = new OnSortEventListener(baseService, example, paging, xulElement);
			for (Column column : (List<Column>) columns.getChildren()) {
				if (column.getSortAscending() != null || column.getSortDescending() != null) {
					column.addEventListener(Events.ON_SORT, onSortEventListener);
				}
			}
		}
	}

	/**
	 * 分页监听器
	 * @author liujiuwu
	 *
	 */
	private final class OnPagingEventListener implements EventListener {
		private BaseService baseService;
		private BaseExample example;
		private Paging paging;
		private XulElement xulElement;

		public OnPagingEventListener(final BaseService baseService, final BaseExample example, final Paging paging, final XulElement xulElement) {
			this.baseService = baseService;
			this.example = example;
			this.paging = paging;
			this.xulElement = xulElement;
		}

		public void onEvent(Event event) throws Exception {
			PagingEvent pe = (PagingEvent) event;
			example.setStart(pe.getActivePage() * example.getPageSize());
			refreshData(baseService, example, paging, xulElement);
		}
	}

	/**
	 * 排序监听器
	 * @author liujiuwu
	 *
	 */
	private final class OnSortEventListener implements EventListener {
		private BaseService baseService;
		private BaseExample example;
		private Paging paging;
		private XulElement xulElement;

		public OnSortEventListener(final BaseService baseService, final BaseExample example, final Paging paging, final XulElement xulElement) {
			this.baseService = baseService;
			this.example = example;
			this.paging = paging;
			this.xulElement = xulElement;
		}

		public void onEvent(Event event) throws Exception {
			if (xulElement instanceof Listbox) {
				Listheader lh = (Listheader) event.getTarget();
				final String sortDirection = lh.getSortDirection();
				if (Constants.ASCENDING.equals(sortDirection)) {
					final Comparator<?> cmpr = lh.getSortDescending();
					if (cmpr instanceof OrderByComparator) {
						String orderBy = ((OrderByComparator) cmpr).getOrderByClause();
						example.setOrderByClause(orderBy + " desc");
					}
				} else if (Constants.DESCENDING.equals(sortDirection) || Constants.NATURAL.equals(sortDirection) || Strings.isBlank(sortDirection)) {
					final Comparator<?> cmpr = lh.getSortAscending();
					if (cmpr instanceof OrderByComparator) {
						String orderBy = ((OrderByComparator) cmpr).getOrderByClause();
						example.setOrderByClause(orderBy + " asc");
					}
				}

			} else if (xulElement instanceof Grid) {
				Column cl = (Column) event.getTarget();
				final String sortDirection = cl.getSortDirection();
				if (Constants.ASCENDING.equals(sortDirection)) {
					final Comparator<?> cmpr = cl.getSortDescending();
					if (cmpr instanceof OrderByComparator) {
						String orderBy = ((OrderByComparator) cmpr).getOrderByClause();
						example.setOrderByClause(orderBy + " desc");
					}
				} else if (Constants.DESCENDING.equals(sortDirection) || Constants.NATURAL.equals(sortDirection) || Strings.isBlank(sortDirection)) {
					final Comparator<?> cmpr = cl.getSortAscending();
					if (cmpr instanceof OrderByComparator) {
						String orderBy = ((OrderByComparator) cmpr).getOrderByClause();
						example.setOrderByClause(orderBy + " asc");
					}
				}
			}
			paging.setActivePage(0);
			example.setStart(paging.getActivePage() * example.getPageSize());
			refreshData(baseService, example, paging, xulElement);
		}
	}
}
