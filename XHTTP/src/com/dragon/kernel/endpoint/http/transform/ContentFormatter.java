package com.dragon.kernel.endpoint.http.transform;

public final class ContentFormatter
{
	public static final String	ATF_INTERNAL	= "___ATF_INTERNAL";

	public static String format(String body, String formatType)
	{
		String formatted = body;
		if ((formatType.indexOf("xml") != -1) || (formatType.indexOf("html") != -1) || (body.startsWith("<")))
		{
			formatted = formatXML(body);
		}
		else if ((formatType.indexOf("json") != -1) || (body.startsWith("{")) || (body.startsWith("[")))
		{
			formatted = formatJSON(body);
		}
		return formatted;
	}

	private static String formatXML(String body)
	{
		body = body.replace('\r', '\n');
		body = body.replace('\f', ' ');
		body = body.replace('\t', ' ');
		StringBuffer sb = new StringBuffer(body);
		int level = 0;
		boolean removePreceeding = false;
		boolean removeInternal = false;
		for (int i = 0; i < sb.length(); i++)
		{
			if (sb.charAt(i) == '>')
			{
				removePreceeding = true;
				removeInternal = false;
			}
			else if (sb.charAt(i) == '<')
			{
				while ((i - 1 > 0) && ((sb.charAt(i - 1) == ' ') || (sb.charAt(i - 1) == '\n')))
				{
					sb.deleteCharAt(i - 1);
					i--;
				}
				removePreceeding = false;
				removeInternal = true;
			}
			else if ((removePreceeding) && ((sb.charAt(i) == '\n') || (sb.charAt(i) == ' ')))
			{
				sb = sb.deleteCharAt(i);
				i--;
			}
			else if ((removeInternal) && (sb.charAt(i) == '\n'))
			{
				sb = sb.replace(i, i + 1, " ");
			}
			else
			{
				removePreceeding = false;
			}
		}
		for (int i = 0; i < sb.length(); i++)
		{
			if ((sb.charAt(i) == '<') && (i > 0))
			{
				sb.insert(i, '\n');
				i++;
			}
			else
			{
				if ((i + 1 >= sb.length()) || (sb.charAt(i) != '>') || (sb.charAt(i + 1) == '<'))
					continue;
				sb.insert(i + 1, '\n');
				i++;
			}
		}
		int prevLevel1 = level;
		for (int i = 0; i < sb.length(); i++)
		{
			if ((i + 3 < sb.length()) && (sb.charAt(i) == '<') && (sb.charAt(i + 1) == '!') && (sb.charAt(i + 2) == '-') && (sb.charAt(i + 3) == '-'))
			{
				i += 3;
				prevLevel1 = level;
			}
			else if ((i + 2 < sb.length()) && (sb.charAt(i) == '-') && (sb.charAt(i + 1) == '-') && (sb.charAt(i + 2) == '>'))
			{
				level = prevLevel1;
				i += 2;
			}
			if ((i + 1 < sb.length()) && (sb.charAt(i) == '<') && (sb.charAt(i + 1) != '/') && (sb.charAt(i + 1) != '!'))
			{
				for (int tabs = 0; tabs < level; tabs++)
				{
					sb.insert(i, '\t');
					i++;
				}
				level++;
			}
			else if ((i + 1 < sb.length()) && (sb.charAt(i) == '<') && (sb.charAt(i + 1) == '/'))
			{
				level--;
				for (int tabs = 0; tabs < level; tabs++)
				{
					sb.insert(i, '\t');
					i++;
				}
			}
			else if ((i + 1 < sb.length()) && (sb.charAt(i) == '/') && (sb.charAt(i + 1) == '>'))
			{
				level--;
			}
			else
			{
				if (((i + 1 >= sb.length()) || (sb.charAt(i) != '\n') || (sb.charAt(i + 1) == '<'))
						&& ((i + 2 >= sb.length()) || (sb.charAt(i + 1) != '<') || (sb.charAt(i + 2) != '!')))
					continue;
				for (int tabs = 0; tabs < level; tabs++)
				{
					sb.insert(i + 1, '\t');
					i++;
				}
			}
		}
		return sb.toString();
	}

	private static String formatJSON(String body)
	{
		StringBuffer sb = new StringBuffer(body);
		boolean waitQuotes = false;
		int level = 0;
		for (int i = 0; i < sb.length(); i++)
		{
			if ((sb.charAt(i) == '"') && (i > 0) && (sb.charAt(i) != '\\'))
			{
				waitQuotes = !waitQuotes;
			}
			else
			{
				if (waitQuotes)
					continue;
				if ((sb.charAt(i) != '\n') && (sb.charAt(i) != '\t'))
					continue;
				sb.deleteCharAt(i);
				i--;
			}
		}

		waitQuotes = false;
		for (int i = 0; i < sb.length(); i++)
		{
			if ((sb.charAt(i) == '"') && (i > 0) && (sb.charAt(i) != '\\'))
			{
				waitQuotes = !waitQuotes;
			}
			else
			{
				if (waitQuotes)
					continue;
				if (sb.charAt(i) == ' ')
				{
					sb.deleteCharAt(i);
					i--;
				}
				else if ((i + 1 < sb.length()) && (sb.charAt(i) == '{'))
				{
					level++;
					sb.insert(i + 1, "\n");
					i++;
					for (int tabs = 0; tabs < level; tabs++)
					{
						sb.insert(i + 1, '\t');
						i++;
					}
				}
				else if (sb.charAt(i) == '}')
				{
					level--;
					sb.insert(i, "\n");
					i++;
					for (int tabs = 0; tabs < level; tabs++)
					{
						sb.insert(i, '\t');
						i++;
					}
				}
				else if (sb.charAt(i) == ',')
				{
					sb.insert(i + 1, "\n");
					i++;
					for (int tabs = 0; tabs < level; tabs++)
					{
						sb.insert(i + 1, '\t');
						i++;
					}
				}
				else if (sb.charAt(i) == '[')
				{
					level++;
					sb.insert(i + 1, "\n");
					i++;
					for (int tabs = 0; tabs < level; tabs++)
					{
						sb.insert(i + 1, '\t');
						i++;
					}
				}
				else if ((i - 1 > 0) && (sb.charAt(i) == ']'))
				{
					level--;
					sb.insert(i, "\n");
					i++;
					for (int tabs = 0; tabs < level; tabs++)
					{
						sb.insert(i, '\t');
						i++;
					}
				}
				else if (sb.charAt(i) == '(')
				{
					level++;
					sb.insert(i + 1, "\n");
					i++;
					for (int tabs = 0; tabs < level; tabs++)
					{
						sb.insert(i + 1, '\t');
						i++;
					}
				}
				else if ((i - 1 > 0) && (sb.charAt(i) == ')'))
				{
					level--;
					sb.insert(i, "\n");
					i++;
					for (int tabs = 0; tabs < level; tabs++)
					{
						sb.insert(i, '\t');
						i++;
					}
				}
				else if (sb.charAt(i) == '\n')
				{
					for (int tabs = 0; tabs < level; tabs++)
					{
						sb.insert(i + 1, '\t');
						i++;
					}
				}
				else
				{
					if (sb.charAt(i) != ':')
						continue;
					sb.insert(i + 1, ' ');
					i++;
				}
			}
		}
		return sb.toString();
	}

	public static String removeFlashingDiv(String htmlNode)
	{
		return htmlNode.replaceAll("<[dD][iI][vV].*[cC][lL][aA][sS][sSO]=\"___ATF_INTERNAL(.*\\s*)*</[dD][iI][vV]>", "");
	}
}