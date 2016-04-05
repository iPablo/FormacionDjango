"""Views.py"""
from django.shortcuts import render
from .models import NewsItem, Event
from .forms import NewsItemForm
from django.views.generic import ListView, CreateView, UpdateView, DeleteView
from django.core.urlresolvers import reverse_lazy
from .serializers import NewsItemSerializer, EventSerializer
from rest_framework import generics

# API


class NewsItemListAPI(generics.ListCreateAPIView):
    """Class NewsItemListAPI"""

    queryset = NewsItem.objects.all()
    serializer_class = NewsItemSerializer


class NewsItemDetailAPI(generics.RetrieveUpdateDestroyAPIView):
    """Class NewsItemDetailAPIAPI"""

    queryset = NewsItem.objects.all()
    serializer_class = NewsItemSerializer


class EventListAPI(generics.ListCreateAPIView):
    """Class EventListAPI"""

    queryset = Event.objects.all()
    serializer_class = EventSerializer


class EventDetailAPI(generics.RetrieveUpdateDestroyAPIView):
    """Class EventDetailAPI"""

    queryset = Event.objects.all()
    serializer_class = EventSerializer

# INDEX


def index(request):
    """INDEX"""
    return render(request, 'NoticiasEventos/index.html', {})

# V1


def v1List(request):
    """v1List"""
    result = NewsItem.objects.all()
    return render(request, 'NoticiasEventos/v1List.html', {'result': result})


def v1Create(request):
    """v1Create."""
    if request.method == "POST":
        form = NewsItemForm(request.POST)

        if form.is_valid():
            post = form.save()
            post.save()

            result = NewsItem.objects.all()
            return render(request, 'NoticiasEventos/v1List.html', {
                'result': result})
    else:
        form = NewsItemForm()

    return render(request, 'NoticiasEventos/v1Create.html', {'form': form})


def v1Update(request, pk):
    """v1Update"""
    if request.method == "GET":
        x = NewsItem.objects.get(pk=pk)
        return render(request, 'NoticiasEventos/v1Update.html', {'inputs': x})

    if request.method == "POST":
        x = NewsItem.objects.get(pk=pk)
        x.title = request.POST["title"]
        x.description = request.POST["description"]
        x.publish_date = request.POST["publish_date"]
        x.save()

        result = NewsItem.objects.all()
        return render(request, 'NoticiasEventos/v1List.html', {
            'result': result})


def v1Delete(request, pk):
    """v1Delete"""
    x = NewsItem.objects.get(pk=pk)
    x.delete()

    result = NewsItem.objects.all()
    return render(request, 'NoticiasEventos/v1List.html', {'result': result})

# V2


class v2List(ListView):
    """Class v2List"""

    model = NewsItem
    context_object_name = "result"
    template_name = "NoticiasEventos/v2List.html"


class v2Create(CreateView):
    """Class v2Create"""

    model = NewsItem
    success_url = reverse_lazy('NoticiasEventos:v2List')
    fields = ['title', 'description', 'publish_date']
    template_name = "NoticiasEventos/v1Create.html"


class v2Update(UpdateView):
    """Class v2Update"""

    model = NewsItem
    success_url = reverse_lazy('NoticiasEventos:v2List')
    fields = ['title', 'description', 'publish_date']
    template_name = "NoticiasEventos/v1Create.html"


class v2Delete(DeleteView):
    """Class v2Delete"""

    model = NewsItem
    success_url = reverse_lazy('NoticiasEventos:v2List')

# EVENT


class EventList(ListView):
    """Class EventList"""

    model = Event
    context_object_name = "result"
    template_name = "NoticiasEventos/eventList.html"


class EventCreate(CreateView):
    """Class EventCreate"""

    model = Event
    success_url = reverse_lazy('NoticiasEventos:eventList')
    fields = ['title', 'description', 'start_date', 'end_date']
    template_name = "NoticiasEventos/eventCreate.html"


class EventUpdate(UpdateView):
    """Class EventUpdate"""

    model = Event
    success_url = reverse_lazy('NoticiasEventos:eventList')
    fields = ['title', 'description', 'start_date', 'end_date']
    template_name = "NoticiasEventos/eventCreate.html"


class EventDelete(DeleteView):
    """Class EventDelete"""

    model = Event
    success_url = reverse_lazy('NoticiasEventos:eventList')
