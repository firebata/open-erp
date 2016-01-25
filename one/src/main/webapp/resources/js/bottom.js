$(function(){
	parent.document.all("mainIframe").style.height=document.body.scrollHeight; 
	console.log("height:"+parent.document.all("mainIframe").style.height);
	parent.document.all("mainIframe").style.width=document.body.scrollWidth;	
});
