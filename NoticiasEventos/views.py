from django.shortcuts import render
from .models import NewsItem, Event
from .forms import NewsItemForm
from django.views.generic import ListView, DetailView, CreateView, UpdateView, DeleteView
from django.core.urlresolvers import reverse_lazy

def index(request):
	return render(request, 'NoticiasEventos/index.html', {})

def v1List(request):
	result  = NewsItem.objects.all()
	
	return render(request, 'NoticiasEventos/v1List.html', {'result': result})

def v1Create(request):
	if request.method == "POST":
		form = NewsItemForm(request.POST)

		if form.is_valid():
			post = form.save()
			post.save()

			result = NewsItem.objects.all()
			return render(request, 'NoticiasEventos/v1List.html', {'result': result})
	else:
		form = NewsItemForm()

	return render(request, 'NoticiasEventos/v1Create.html', {'form': form})

def v1Update(request, pk):
	if request.method == "GET":
		x = NewsItem.objects.get(pk=pk)
		return render(request, 'NoticiasEventos/v1Update.html', {'inputs': x})

	if request.method == "POST":
		x = NewsItem.objects.get(pk=pk)
		x.title = request.POST["title"]
		x.description = request.POST["description"]
		x.publish_date = request.POST["publish_date"]
		x.save()

		result  = NewsItem.objects.all()
		return render(request, 'NoticiasEventos/v1List.html', {'result': result})

def v1Delete(request, pk):
	x = NewsItem.objects.get(pk=pk)
	x.delete()

	result  = NewsItem.objects.all()
	return render(request, 'NoticiasEventos/v1List.html', {'result': result})

class v2List(ListView):
	model = NewsItem
	context_object_name = "result"
	template_name = "NoticiasEventos/v2List.html"

class v2Create(CreateView):
	model = NewsItem
	success_url = reverse_lazy('NoticiasEventos:v2List')
	fields = ['title', 'description', 'publish_date']
	template_name = "NoticiasEventos/v1Create.html"

class v2Update(UpdateView):
	model = NewsItem
	success_url = reverse_lazy('NoticiasEventos:v2List')
	fields = ['title', 'description', 'publish_date']
	template_name = "NoticiasEventos/v1Create.html"
		
class v2Delete(DeleteView):
	model = NewsItem
	success_url = reverse_lazy('NoticiasEventos:v2List')

