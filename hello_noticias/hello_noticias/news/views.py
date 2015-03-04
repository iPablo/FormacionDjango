from django.contrib import messages
from django.contrib.messages.views import SuccessMessageMixin
from django.core.exceptions import ObjectDoesNotExist
from django.core.urlresolvers import reverse
from django.http import HttpResponseRedirect
from django.shortcuts import render_to_response
from django.template import RequestContext
from django.views.generic import ListView
from django.views.generic.base import TemplateView
from django.views.generic.edit import CreateView, UpdateView

from hello_noticias.news.forms import NewsForm
from hello_noticias.news.models import News


def index(request):
    return render_to_response('news/news.html',
                              context_instance=RequestContext(request))


def create(request):
    data = None
    if request.method == 'POST':
        data = request.POST
    form = NewsForm(data=data)
    if form.is_valid():
        form.save()
        messages.success(request, 'News created successfully')
        return HttpResponseRedirect(reverse('news_index'))
    return render_to_response('news/create.html',
                              {'form': form},
                              context_instance=RequestContext(request))


def list_news(request):
    return render_to_response('news/list_news.html',
                              context_instance=RequestContext(request))


def update(request, new_id):
    data = None
    my_new = None
    if request.method == 'POST':
        data = request.POST
    try:
        my_new = News.objects.get(id=new_id)
    except ObjectDoesNotExist:
        messages.error(request,
                       'There is no news with that ID number. Please try again'
                       )
        return HttpResponseRedirect(reverse('news_index'))
    form = NewsForm(data=data, instance=my_new)
    if form.is_valid():
        form.save()
        messages.success(request, 'Update completed')
        return HttpResponseRedirect(reverse('news_index'))
    return render_to_response('news/update.html',
                              {'form': form},
                              context_instance=RequestContext(request))


class NewsIndex(TemplateView):
    template_name = 'news/news.html'
    http_method_names = ('get',)


class NewsList(ListView):
    model = News
    template_name = 'news/list_news.html'
    http_method_names = ('get',)


class NewsCreate(SuccessMessageMixin, CreateView):
    form_class = NewsForm
    fields = ('title', 'description')
    success_message = "News was created successfully"
    template_name = 'news/create.html'
    http_method_names = ('get', 'post')


class NewsUpdate(SuccessMessageMixin, UpdateView):
    form_class = NewsForm
    model = News
    fields = ('title', 'description')
    success_message = 'Update completed'
    template_name = 'news/update.html'
    pk_url_kwarg = 'new_id'
    http_method_names = ('get', 'post')
