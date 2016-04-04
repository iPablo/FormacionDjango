from django.core.urlresolvers import reverse_lazy
from django.views.generic import ListView
from django.views.generic.detail import DetailView
from django.views.generic.edit import (
        CreateView,
        UpdateView,
        DeleteView
    )
from .models import NewsItem
from django.shortcuts import render
from django.utils import timezone
from .models import Event
from django.shortcuts import redirect, get_object_or_404
from .forms import NewsItemForm, EventForm


def newsitem_list1(request, template_name='blog/newsitem_list.html'):
    posts = NewsItem.objects.all()
    data = {}
    data['object_list'] = posts
    return render(request, template_name, data)


def newsitem_create(request, template_name='blog/newsitem_form.html'):
    form = NewsItemForm(request.POST or None)
    if form.is_valid():
        form.save()
        return redirect('blog:noticias')
    return render(request, template_name, {'form': form})


def newsitem_update(request, pk, template_name='blog/newsitem_form.html'):
    post = get_object_or_404(NewsItem, pk=pk)
    form = NewsItemForm(request.POST or None, instance=post)
    if form.is_valid():
        form.save()
        return redirect('blog:noticias')
    return render(request, template_name, {'form': form})


def newsitem_delete(request, pk, template_name='blog/newsitem_confirm_delete.html'):
    post = get_object_or_404(NewsItem, pk=pk)
    if request.method == 'POST':
        post.delete()
        return redirect('blog:noticias')
    return render(request, template_name, {'object': post})


def index(request):
    return render(request, 'blog/index.html', {})


def todo(request):
    post = NewsItem.objects.filter(publish_date__lte=timezone.now()).order_by('publish_date')
    event = Event.objects.filter(start_date__lte=timezone.now()).order_by('start_date')
    return render(request, 'blog/both_list.html', {'NewsItem': post, 'Event': event})


def newsitem_list(request):
    post = NewsItem.objects.filter(publish_date__lte=timezone.now()).order_by('publish_date')
    return render(request, 'blog/newsitem_list.html', {'NewsItem': post})


class NewsItemList(ListView):
        model = NewsItem


class NewsItemDetail(DetailView):
        model = NewsItem


class NewsItemCreation(CreateView):
        model = NewsItem
        success_url = reverse_lazy('blog:noticias')
        #ields = ['title', 'description', 'publish_date']
        form_class = NewsItemForm

class NewsItemUpdate(UpdateView):
        model = NewsItem
        success_url = reverse_lazy('blog:noticias')
        fields = ['title', 'description', 'publish_date']


class NewsItemDelete(DeleteView):
        model = NewsItem
        success_url = reverse_lazy('blog:noticias')


def event_list(request):
    event = Event.objects.all().order_by('start_date')
    return render(request, 'blog/event_list.html', {'Event': event})


class EventList(ListView):
        model = Event


class EventDetail(DetailView):
        model = Event


class EventCreation(CreateView):
        model = Event
        success_url = reverse_lazy('blog:eventos')
        """fields = ['title', 'description', 'start_date', 'end_date']"""
        form_class = EventForm



class EventUpdate(UpdateView):
        model = Event
        success_url = reverse_lazy('blog:eventos')
        fields = ['title', 'description', 'start_date', 'end_date']


class EventDelete(DeleteView):
        model = Event
        success_url = reverse_lazy('blog:eventos')
