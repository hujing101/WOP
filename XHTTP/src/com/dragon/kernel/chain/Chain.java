package com.dragon.kernel.chain;

import java.util.ArrayList;
import java.util.List;

public class Chain
{
	protected List<Filter>	before;
	protected List<Filter>	after;

	public Chain()
	{
		this.before = new ArrayList<Filter>();

		this.after = new ArrayList<Filter>();
	}

	public void setAfter(List<Filter> after)
	{
		if (this.after == null)
		{
			this.after = after;
		}
		else
		{
			this.after.addAll(after);
		}
	}

	public void setBefore(List<Filter> before)
	{
		if (this.before == null)
		{
			this.before = before;
		}
		else
		{
			this.before.addAll(before);
		}
	}

	public Filter getBeforeFilter()
	{
		return assemble(this.before, "before");
	}

	public Filter getAfterFilter()
	{
		return assemble(this.after, "after");
	}

	private Filter assemble(List<Filter> filter, String flag)
	{
		int size = filter.size();

		Filter entrypoint = size > 0 ? (Filter) filter.get(0) : null;

		for (int i = 0; i <= size; i++)
		{
			if (i + 1 >= size)
				continue;
			if ("before".equals(flag))
			{
				((Filter) filter.get(i)).setNextBeforeFilter((Filter) filter.get(i + 1));
			}
			else
			{
				if (!"after".equals(flag))
					continue;
				((Filter) filter.get(i)).setNextAfterFilter((Filter) filter.get(i + 1));
			}

		}

		return entrypoint;
	}
}