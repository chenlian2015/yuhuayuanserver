	function MakeForm(arrayId, path) {
		// 创建一个 form 
		var form1 = document.createElement("form");
		form1.id = "form1";
		form1.name = "form1";
		// 添加到 body 中 
		document.body.appendChild(form1);
		
		
		for(var id in arrayId)
		{
			var input = document.createElement("input");
			// 设置相应参数 
			input.type = "text";
			input.name = $("#"+arrayId[id]+"").attr('name');
			if($("#"+arrayId[id]+"")[0].tagName== "IMG")
				{
				input.value = $("#"+arrayId[id]+"").attr('src');
				}
			else
				{
				input.value = $("#"+arrayId[id]+"").val();
				}

			// 将该输入框插入到 form 中 
			form1.appendChild(input);
		}
		// 创建一个输入 
		 // form 的提交方式 
		form1.method = "POST";
		// form 提交路径 
		form1.action = path;
		// 对该 form 执行提交 
		form1.submit();
		// 删除该 form 

		document.body.removeChild(form1);
	}
	
	
	function MakeFormNameValue(name, value, path) {
		// 创建一个 form 
		var form1 = document.createElement("form");
		form1.id = "form1";
		form1.name = "form1";
		// 添加到 body 中 
		document.body.appendChild(form1);
		
		

			var input = document.createElement("input");
			// 设置相应参数 
			input.type = "text";
			input.name = name;
			input.value = value

			// 将该输入框插入到 form 中 
			form1.appendChild(input);

		// 创建一个输入 
		 // form 的提交方式 
		form1.method = "POST";
		// form 提交路径 
		form1.action = path;
		// 对该 form 执行提交 
		form1.submit();
		// 删除该 form 

		document.body.removeChild(form1);
	}