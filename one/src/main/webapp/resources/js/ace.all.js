/**
 <b>Ace custom scroller</b>. It is not as feature-rich as plugins such as NiceScroll but it's good enough for most cases.
*/
(function($ , undefined) {
	var Ace_Scroll = function(element , _settings) {
		var self = this;
		
		var attrib_values = ace.helper.getAttrSettings(element, $.fn.ace_scroll.defaults);
		var settings = $.extend({}, $.fn.ace_scroll.defaults, _settings, attrib_values);
	
		this.size = 0;
		this.lock = false;
		this.lock_anyway = false;
		
		this.$element = $(element);
		this.element = element;
		
		var vertical = true;

		var disabled = false;
		var active = false;
		var created = false;

		var $content_wrap = null, content_wrap = null;
		var $track = null, $bar = null, track = null, bar = null;
		var bar_style = null;
		
		var bar_size = 0, bar_pos = 0, bar_max_pos = 0, bar_size_2 = 0, move_bar = true;
		var reset_once = false;
		
		var styleClass = '';
		var trackFlip = false;//vertical on left or horizontal on top
		var trackSize = 0;

		var css_pos,
			css_size,
			max_css_size,
			client_size,
			scroll_direction,
			scroll_size;

		var ratio = 1;
		var inline_style = false;
		var mouse_track = false;
		var mouse_release_target = 'onmouseup' in window ? window : 'html';
		var dragEvent = settings.dragEvent || false;
		
		var trigger_scroll = _settings.scrollEvent || false;
		
		
		var detached = settings.detached || false;//when detached, hideOnIdle as well?
		var updatePos = settings.updatePos || false;//default is true
		
		var hideOnIdle = settings.hideOnIdle || false;
		var hideDelay = settings.hideDelay || 1500;
		var insideTrack = false;//used to hide scroll track when mouse is up and outside of track
		var observeContent = settings.observeContent || false;
		var prevContentSize = 0;
		
		var is_dirty = true;//to prevent consecutive 'reset' calls
		
		this.create = function(_settings) {
			if(created) return;
			//if(disabled) return;
			if(_settings) settings = $.extend({}, $.fn.ace_scroll.defaults, _settings);

			this.size = parseInt(this.$element.attr('data-size')) || settings.size || 200;
			vertical = !settings['horizontal'];

			css_pos = vertical ? 'top' : 'left';//'left' for horizontal
			css_size = vertical ? 'height' : 'width';//'width' for horizontal
			max_css_size = vertical ? 'maxHeight' : 'maxWidth';

			client_size = vertical ? 'clientHeight' : 'clientWidth';
			scroll_direction = vertical ? 'scrollTop' : 'scrollLeft';
			scroll_size = vertical ? 'scrollHeight' : 'scrollWidth';



			this.$element.addClass('ace-scroll');
			if(this.$element.css('position') == 'static') {
				inline_style = this.element.style.position;
				this.element.style.position = 'relative';
			} else inline_style = false;

			var scroll_bar = null;
			if(!detached) {
				this.$element.wrapInner('<div class="scroll-content" />');
				this.$element.prepend('<div class="scroll-track"><div class="scroll-bar"></div></div>');
			}
			else {
				scroll_bar = $('<div class="scroll-track scroll-detached"><div class="scroll-bar"></div></div>').appendTo('body');
			}


			$content_wrap = this.$element;
			if(!detached) $content_wrap = this.$element.find('.scroll-content').eq(0);
			
			if(!vertical) $content_wrap.wrapInner('<div />');
			
			content_wrap = $content_wrap.get(0);
			if(detached) {
				//set position for detached scrollbar
				$track = scroll_bar;
				setTrackPos();
			}
			else $track = this.$element.find('.scroll-track').eq(0);
			
			$bar = $track.find('.scroll-bar').eq(0);
			track = $track.get(0);
			bar = $bar.get(0);
			bar_style = bar.style;

			//add styling classes and horizontalness
			if(!vertical) $track.addClass('scroll-hz');
			if(settings.styleClass) {
				styleClass = settings.styleClass;
				$track.addClass(styleClass);
				trackFlip = !!styleClass.match(/scroll\-left|scroll\-top/);
			}
			
			//calculate size of track!
			if(trackSize == 0) {
				$track.show();
				getTrackSize();
			}
			
			$track.hide();
			

			//if(!touchDrag) {
			$track.on('mousedown', mouse_down_track);
			$bar.on('mousedown', mouse_down_bar);
			//}

			$content_wrap.on('scroll', function() {
				if(move_bar) {
					bar_pos = parseInt(Math.round(this[scroll_direction] * ratio));
					bar_style[css_pos] = bar_pos + 'px';
				}
				move_bar = false;
				if(trigger_scroll) this.$element.trigger('scroll', [content_wrap]);
			})


			if(settings.mouseWheel) {
				this.lock = settings.mouseWheelLock;
				this.lock_anyway = settings.lockAnyway;

				//mousewheel library available?
				this.$element.on(!!$.event.special.mousewheel ? 'mousewheel.ace_scroll' : 'mousewheel.ace_scroll DOMMouseScroll.ace_scroll', function(event) {
					if(disabled) return;
					checkContentChanges(true);

					if(!active) return !self.lock_anyway;

					if(mouse_track) {
						mouse_track = false;
						$('html').off('.ace_scroll')
						$(mouse_release_target).off('.ace_scroll');
						if(dragEvent) self.$element.trigger('drag.end');
					}
					

					event.deltaY = event.deltaY || 0;
					var delta = (event.deltaY > 0 || event.originalEvent.detail < 0 || event.originalEvent.wheelDelta > 0) ? 1 : -1
					var scrollEnd = false//have we reached the end of scrolling?
					
					var clientSize = content_wrap[client_size], scrollAmount = content_wrap[scroll_direction];
					if( !self.lock ) {
						if(delta == -1)	scrollEnd = (content_wrap[scroll_size] <= scrollAmount + clientSize);
						else scrollEnd = (scrollAmount == 0);
					}

					self.move_bar(true);

					//var step = parseInt( Math.min(Math.max(parseInt(clientSize / 8) , 80) , self.size) ) + 1;
					var step = parseInt(clientSize / 8);
					if(step < 80) step = 80;
					if(step > self.size) step = self.size;
					step += 1;
					
					content_wrap[scroll_direction] = scrollAmount - (delta * step);


					return scrollEnd && !self.lock_anyway;
				})
			}
			
			
			//swipe not available yet
			var touchDrag = ace.vars['touch'] && 'ace_drag' in $.event.special && settings.touchDrag //&& !settings.touchSwipe;
			//add drag event for touch devices to scroll
			if(touchDrag/** || ($.fn.swipe && settings.touchSwipe)*/) {
				var dir = '', event_name = touchDrag ? 'ace_drag' : 'swipe';
				this.$element.on(event_name + '.ace_scroll', function(event) {
					if(disabled) {
						event.retval.cancel = true;
						return;
					}
					checkContentChanges(true);
					
					if(!active) {
						event.retval.cancel = this.lock_anyway;
						return;
					}

					dir = event.direction;
					if( (vertical && (dir == 'up' || dir == 'down'))
						||
						(!vertical && (dir == 'left' || dir == 'right'))
					   )
					{
						var distance = vertical ? event.dy : event.dx;

						if(distance != 0) {
							if(Math.abs(distance) > 20 && touchDrag) distance = distance * 2;

							self.move_bar(true);
							content_wrap[scroll_direction] = content_wrap[scroll_direction] + distance;
						}
					}
					
				})
			}
			
			
			/////////////////////////////////
			
			if(hideOnIdle) {
				$track.addClass('idle-hide');
			}
			if(observeContent) {
				$track.on('mouseenter.ace_scroll', function() {
					insideTrack = true;
					checkContentChanges(false);
				}).on('mouseleave.ace_scroll', function() {
					insideTrack = false;
					if(mouse_track == false) hideScrollbars();
				});
			}


			
			//some mobile browsers don't have mouseenter
			this.$element.on('mouseenter.ace_scroll touchstart.ace_scroll', function(e) {
				//if(ace.vars['old_ie']) return;//IE8 has a problem triggering event two times and strangely wrong values for this.size especially in fullscreen widget!
				
				is_dirty = true;
				if(observeContent) checkContentChanges(true);
				else if(settings.hoverReset) self.reset(true);
				
				$track.addClass('scroll-hover');
			}).on('mouseleave.ace_scroll touchend.ace_scroll', function() {
				$track.removeClass('scroll-hover');
			});
			//

			if(!vertical) $content_wrap.children(0).css(css_size, this.size);//the extra wrapper
			$content_wrap.css(max_css_size , this.size);
			
			disabled = false;
			created = true;
		}
		this.is_active = function() {
			return active;
		}
		this.is_enabled = function() {
			return !disabled;
		}
		this.move_bar = function($move) {
			move_bar = $move;
		}
		
		this.get_track = function() {
			return track;
		}

		this.reset = function(innert_call) {
			if(disabled) return;// this;
			if(!created) this.create();
			/////////////////////
			var size = this.size;
			
			if(innert_call && !is_dirty) {
				return;
			}
			is_dirty = false;

			if(detached) {
				var border_size = parseInt(Math.round( (parseInt($content_wrap.css('border-top-width')) + parseInt($content_wrap.css('border-bottom-width'))) / 2.5 ));//(2.5 from trial?!)
				size -= border_size;//only if detached
			}
	
			var content_size   = vertical ? content_wrap[scroll_size] : size;
			if( (vertical && content_size == 0) || (!vertical && this.element.scrollWidth == 0) ) {
				//element is hidden
				//this.$element.addClass('scroll-hidden');
				$track.removeClass('scroll-active')
				return;// this;
			}

			var available_space = vertical ? size : content_wrap.clientWidth;

			if(!vertical) $content_wrap.children(0).css(css_size, size);//the extra wrapper
			$content_wrap.css(max_css_size , this.size);
			

			if(content_size > available_space) {
				active = true;
				$track.css(css_size, available_space).show();

				ratio = parseFloat((available_space / content_size).toFixed(5))
				
				bar_size = parseInt(Math.round(available_space * ratio));
				bar_size_2 = parseInt(Math.round(bar_size / 2));

				bar_max_pos = available_space - bar_size;
				bar_pos = parseInt(Math.round(content_wrap[scroll_direction] * ratio));

				bar_style[css_size] = bar_size + 'px';
				bar_style[css_pos] = bar_pos + 'px';
				
				$track.addClass('scroll-active');
				
				if(trackSize == 0) {
					getTrackSize();
				}

				if(!reset_once) {
					//this.$element.removeClass('scroll-hidden');
					if(settings.reset) {
						//reset scrollbar to zero position at first							
						content_wrap[scroll_direction] = 0;
						bar_style[css_pos] = 0;
					}
					reset_once = true;
				}
				
				if(detached) setTrackPos();
			} else {
				active = false;
				$track.hide();
				$track.removeClass('scroll-active');
				$content_wrap.css(max_css_size , '');
			}

			return;// this;
		}
		this.disable = function() {
			content_wrap[scroll_direction] = 0;
			bar_style[css_pos] = 0;

			disabled = true;
			active = false;
			$track.hide();
			
			this.$element.addClass('scroll-disabled');
			
			$track.removeClass('scroll-active');
			$content_wrap.css(max_css_size , '');
		}
		this.enable = function() {
			disabled = false;
			this.$element.removeClass('scroll-disabled');
		}
		this.destroy = function() {
			active = false;
			disabled = false;
			created = false;
			
			this.$element.removeClass('ace-scroll scroll-disabled scroll-active');
			this.$element.off('.ace_scroll')

			if(!detached) {
				if(!vertical) {
					//remove the extra wrapping div
					$content_wrap.find('> div').children().unwrap();
				}
				$content_wrap.children().unwrap();
				$content_wrap.remove();
			}
			
			$track.remove();
			
			if(inline_style !== false) this.element.style.position = inline_style;
			
			if(idleTimer != null) {
				clearTimeout(idleTimer);
				idleTimer = null;
			}
		}
		this.modify = function(_settings) {
			if(_settings) settings = $.extend({}, settings, _settings);
			
			this.destroy();
			this.create();
			is_dirty = true;
			this.reset(true);
		}
		this.update = function(_settings) {
			if(_settings) settings = $.extend({}, settings, _settings);
		
			this.size = _settings.size || this.size;
			
			this.lock = _settings.mouseWheelLock || this.lock;
			this.lock_anyway = _settings.lockAnyway || this.lock_anyway;
			
			if(_settings.styleClass != undefined) {
				if(styleClass) $track.removeClass(styleClass);
				styleClass = _settings.styleClass;
				if(styleClass) $track.addClass(styleClass);
				trackFlip = !!styleClass.match(/scroll\-left|scroll\-top/);
			}
		}
		
		this.start = function() {
			content_wrap[scroll_direction] = 0;
		}
		this.end = function() {
			content_wrap[scroll_direction] = content_wrap[scroll_size];
		}
		
		this.hide = function() {
			$track.hide();
		}
		this.show = function() {
			$track.show();
		}

		
		this.update_scroll = function() {
			move_bar = false;
			bar_style[css_pos] = bar_pos + 'px';
			content_wrap[scroll_direction] = parseInt(Math.round(bar_pos / ratio));
		}

		function mouse_down_track(e) {
			e.preventDefault();
			e.stopPropagation();
				
			var track_offset = $track.offset();
			var track_pos = track_offset[css_pos];//top for vertical, left for horizontal
			var mouse_pos = vertical ? e.pageY : e.pageX;
			
			if(mouse_pos > track_pos + bar_pos) {
				bar_pos = mouse_pos - track_pos - bar_size + bar_size_2;
				if(bar_pos > bar_max_pos) {						
					bar_pos = bar_max_pos;
				}
			}
			else {
				bar_pos = mouse_pos - track_pos - bar_size_2;
				if(bar_pos < 0) bar_pos = 0;
			}

			self.update_scroll()
		}

		var mouse_pos1 = -1, mouse_pos2 = -1;
		function mouse_down_bar(e) {
			e.preventDefault();
			e.stopPropagation();

			if(vertical) {
				mouse_pos2 = mouse_pos1 = e.pageY;
			} else {
				mouse_pos2 = mouse_pos1 = e.pageX;
			}

			mouse_track = true;
			$('html').off('mousemove.ace_scroll').on('mousemove.ace_scroll', mouse_move_bar)
			$(mouse_release_target).off('mouseup.ace_scroll').on('mouseup.ace_scroll', mouse_up_bar);
			
			$track.addClass('active');
			if(dragEvent) self.$element.trigger('drag.start');
		}
		function mouse_move_bar(e) {
			e.preventDefault();
			e.stopPropagation();

			if(vertical) {
				mouse_pos2 = e.pageY;
			} else {
				mouse_pos2 = e.pageX;
			}
			

			if(mouse_pos2 - mouse_pos1 + bar_pos > bar_max_pos) {
				mouse_pos2 = mouse_pos1 + bar_max_pos - bar_pos;
			} else if(mouse_pos2 - mouse_pos1 + bar_pos < 0) {
				mouse_pos2 = mouse_pos1 - bar_pos;
			}
			bar_pos = bar_pos + (mouse_pos2 - mouse_pos1);

			mouse_pos1 = mouse_pos2;

			if(bar_pos < 0) {
				bar_pos = 0;
			}
			else if(bar_pos > bar_max_pos) {
				bar_pos = bar_max_pos;
			}
			
			self.update_scroll()
		}
		function mouse_up_bar(e) {
			e.preventDefault();
			e.stopPropagation();
			
			mouse_track = false;
			$('html').off('.ace_scroll')
			$(mouse_release_target).off('.ace_scroll');

			$track.removeClass('active');
			if(dragEvent) self.$element.trigger('drag.end');
			
			if(active && hideOnIdle && !insideTrack) hideScrollbars();
		}
		
		
		var idleTimer = null;
		var prevCheckTime = 0;
		function checkContentChanges(hideSoon) {
			//check if content size has been modified since last time?
			//and with at least 1s delay
			var newCheck = +new Date();
			if(observeContent && newCheck - prevCheckTime > 1000) {
				var newSize = content_wrap[scroll_size];
				if(prevContentSize != newSize) {
					prevContentSize = newSize;
					is_dirty = true;
					self.reset(true);
				}
				prevCheckTime = newCheck;
			}
			
			//show scrollbars when not idle anymore i.e. triggered by mousewheel, dragging, etc
			if(active && hideOnIdle) {
				if(idleTimer != null) {
					clearTimeout(idleTimer);
					idleTimer = null;
				}
				$track.addClass('not-idle');
			
				if(!insideTrack && hideSoon == true) {
					//hideSoon is false when mouse enters track
					hideScrollbars();
				}
			}
		}

		function hideScrollbars() {
			if(idleTimer != null) {
				clearTimeout(idleTimer);
				idleTimer = null;
			}
			idleTimer = setTimeout(function() {
				idleTimer = null;
				$track.removeClass('not-idle');
			} , hideDelay);
		}
		
		//for detached scrollbars
		function getTrackSize() {
			$track.css('visibility', 'hidden').addClass('scroll-hover');
			if(vertical) trackSize = parseInt($track.outerWidth()) || 0;
			 else trackSize = parseInt($track.outerHeight()) || 0;
			$track.css('visibility', '').removeClass('scroll-hover');
		}
		this.track_size = function() {
			if(trackSize == 0) getTrackSize();
			return trackSize;
		}
		
		//for detached scrollbars
		function setTrackPos() {
			if(updatePos === false) return;
		
			var off = $content_wrap.offset();//because we want it relative to parent not document
			var left = off.left;
			var top = off.top;

			if(vertical) {
				if(!trackFlip) {
					left += ($content_wrap.outerWidth() - trackSize)
				}
			}
			else {
				if(!trackFlip) {
					top += ($content_wrap.outerHeight() - trackSize)
				}
			}
			
			if(updatePos === true) $track.css({top: parseInt(top), left: parseInt(left)});
			else if(updatePos === 'left') $track.css('left', parseInt(left));
			else if(updatePos === 'top') $track.css('top', parseInt(top));
		}
		


		this.create();
		is_dirty = true;
		this.reset(true);
		prevContentSize = content_wrap[scroll_size];

		return this;
	}

	
	$.fn.ace_scroll = function (option,value) {
		var retval;

		var $set = this.each(function () {
			var $this = $(this);
			var data = $this.data('ace_scroll');
			var options = typeof option === 'object' && option;

			if (!data) $this.data('ace_scroll', (data = new Ace_Scroll(this, options)));
			 //else if(typeof options == 'object') data['modify'](options);
			if (typeof option === 'string') retval = data[option](value);
		});

		return (retval === undefined) ? $set : retval;
	};


	$.fn.ace_scroll.defaults = {
		'size' : 200,
		'horizontal': false,
		'mouseWheel': true,
		'mouseWheelLock': false,
		'lockAnyway': false,
		'styleClass' : false,
		
		'observeContent': false,
		'hideOnIdle': false,
		'hideDelay': 1500,
		
		'hoverReset': true //reset scrollbar sizes on mouse hover because of possible sizing changes
		,
		'reset': false //true= set scrollTop = 0
		,
		'dragEvent': false
		,
		'touchDrag': true
		,
		'touchSwipe': false
		,
		'scrollEvent': false //trigger scroll event

		,
		'detached': false
		,
		'updatePos': true
		/**
		,		
		'track' : true,
		'show' : false,
		'dark': false,
		'alwaysVisible': false,
		'margin': false,
		'thin': false,
		'position': 'right'
		*/
     }

	/**
	$(document).on('ace.settings.ace_scroll', function(e, name) {
		if(name == 'sidebar_collapsed') $('.ace-scroll').scroller('reset');
	});
	$(window).on('resize.ace_scroll', function() {
		$('.ace-scroll').scroller('reset');
	});
	*/

})(window.jQuery);/**
 <b>Custom color picker element</b>. Converts html select elements to a dropdown color picker.
*/
(function($ , undefined) {
	var Ace_Colorpicker = function(element, _options) {

		var attrib_values = ace.helper.getAttrSettings(element, $.fn.ace_colorpicker.defaults);
		var options = $.extend({}, $.fn.ace_colorpicker.defaults, _options, attrib_values);


		var $element = $(element);
		var color_list = '';
		var color_selected = '';
		var selection = null;
		var color_array = [];
		
		$element.addClass('hide').find('option').each(function() {
			var $class = 'colorpick-btn';
			var color = this.value.replace(/[^\w\s,#\(\)\.]/g, '');
			if(this.value != color) this.value = color;
			if(this.selected) {
				$class += ' selected';
				color_selected = color;
			}
			color_array.push(color)
			color_list += '<li><a class="'+$class+'" href="#" style="background-color:'+color+';" data-color="'+color+'"></a></li>';
		}).
		end()
		.on('change.color', function(){
			$element.next().find('.btn-colorpicker').css('background-color', this.value);
		})
		.after('<div class="dropdown dropdown-colorpicker">\
		<a data-toggle="dropdown" class="dropdown-toggle" '+(options.auto_pos ? 'data-position="auto"' : '')+' href="#"><span class="btn-colorpicker" style="background-color:'+color_selected+'"></span></a><ul class="dropdown-menu'+(options.caret? ' dropdown-caret' : '')+(options.pull_right ? ' dropdown-menu-right' : '')+'">'+color_list+'</ul></div>')

		
		var dropdown = $element.next().find('.dropdown-menu')
		dropdown.on(ace.click_event, function(e) {
			var a = $(e.target);
			if(!a.is('.colorpick-btn')) return false;

			if(selection) selection.removeClass('selected');
			selection = a;
			selection.addClass('selected');
			var color = selection.data('color');

			$element.val(color).trigger('change');

			e.preventDefault();
			return true;//to hide dropdown
		})
		selection = $element.next().find('a.selected');

		this.pick = function(index, insert) {
			if(typeof index === 'number') {
				if(index >= color_array.length) return;
				element.selectedIndex = index;
				dropdown.find('a:eq('+index+')').trigger(ace.click_event);
			}
			else if(typeof index === 'string') {
				var color = index.replace(/[^\w\s,#\(\)\.]/g, '');
				index = color_array.indexOf(color);
				//add this color if it doesn't exist
				if(index == -1 && insert === true) {
					color_array.push(color);
					
					$('<option />')
					.appendTo($element)
					.val(color);
					
					$('<li><a class="colorpick-btn" href="#"></a></li>')
					.appendTo(dropdown)
					.find('a')
					.css('background-color', color)
					.data('color', color);
					
					index = color_array.length - 1;
				}
				if(index == -1) return;
				dropdown.find('a:eq('+index+')').trigger(ace.click_event);
			}
		}

		this.destroy = function() {
			$element.removeClass('hide').off('change.color')
			.next().remove();
			color_array = [];
		}
	}


	$.fn.ace_colorpicker = function(option, value) {
		var retval;

		var $set = this.each(function () {
			var $this = $(this);
			var data = $this.data('ace_colorpicker');
			var options = typeof option === 'object' && option;

			if (!data) $this.data('ace_colorpicker', (data = new Ace_Colorpicker(this, options)));
			if (typeof option === 'string') retval = data[option](value);
		});

		return (retval === undefined) ? $set : retval;
	}
	
	$.fn.ace_colorpicker.defaults = {
		'pull_right' : false,
		'caret': true,
		'auto_pos': true
	}
	
})(window.jQuery);/**
 <b>Ace file input element</b>. Custom, simple file input element to style browser's default file input.
*/
(function($ , undefined) {
	var multiplible = 'multiple' in document.createElement('INPUT');
	var hasFileList = 'FileList' in window;//file list enabled in modern browsers
	var hasFileReader = 'FileReader' in window;
	var hasFile = 'File' in window;

	var Ace_File_Input = function(element , settings) {
		var self = this;
		
		var attrib_values = ace.helper.getAttrSettings(element, $.fn.ace_file_input.defaults);
		this.settings = $.extend({}, $.fn.ace_file_input.defaults, settings, attrib_values);

		this.$element = $(element);
		this.element = element;
		this.disabled = false;
		this.can_reset = true;
		

		this.$element
		.off('change.ace_inner_call')
		.on('change.ace_inner_call', function(e , ace_inner_call){
			if(self.disabled) return;
		
			if(ace_inner_call === true) return;//this change event is called from above drop event and extra checkings are taken care of there
			return handle_on_change.call(self);
			//if(ret === false) e.preventDefault();
		});

		var parent_label = this.$element.closest('label').css({'display':'block'})
		var tagName = parent_label.length == 0 ? 'label' : 'span';//if not inside a "LABEL" tag, use "LABEL" tag, otherwise use "SPAN"
		this.$element.wrap('<'+tagName+' class="ace-file-input" />');

		this.apply_settings();
		this.reset_input_field();//for firefox as it keeps selected file after refresh
	}
	Ace_File_Input.error = {
		'FILE_LOAD_FAILED' : 1,
		'IMAGE_LOAD_FAILED' : 2,
		'THUMBNAIL_FAILED' : 3
	};


	Ace_File_Input.prototype.apply_settings = function() {
		var self = this;

		this.multi = this.$element.attr('multiple') && multiplible;
		this.well_style = this.settings.style == 'well';

		if(this.well_style) this.$element.parent().addClass('ace-file-multiple');
		 else this.$element.parent().removeClass('ace-file-multiple');


		this.$element.parent().find(':not(input[type=file])').remove();//remove all except our input, good for when changing settings
		this.$element.after('<span class="ace-file-container" data-title="'+this.settings.btn_choose+'"><span class="ace-file-name" data-title="'+this.settings.no_file+'">'+(this.settings.no_icon ? '<i class="'+ ace.vars['icon'] + this.settings.no_icon+'"></i>' : '')+'</span></span>');
		this.$label = this.$element.next();
		this.$container = this.$element.closest('.ace-file-input');

		var remove_btn = !!this.settings.icon_remove;
		if(remove_btn) {
			var btn = 
			$('<a class="remove" href="#"><i class="'+ ace.vars['icon'] + this.settings.icon_remove+'"></i></a>')
			.appendTo(this.$element.parent());

			btn.on(ace.click_event, function(e){
				e.preventDefault();
				if( !self.can_reset ) return false;
				
				var ret = true;
				if(self.settings.before_remove) ret = self.settings.before_remove.call(self.element);
				if(!ret) return false;

				var r = self.reset_input();
				return false;
			});
		}


		if(this.settings.droppable && hasFileList) {
			enable_drop_functionality.call(this);
		}
	}

	Ace_File_Input.prototype.show_file_list = function($files , inner_call) {
		var files = typeof $files === "undefined" ? this.$element.data('ace_input_files') : $files;
		if(!files || files.length == 0) return;
		
		//////////////////////////////////////////////////////////////////
		
		if(this.well_style) {
			this.$label.find('.ace-file-name').remove();
			if(!this.settings.btn_change) this.$label.addClass('hide-placeholder');
		}
		this.$label.attr('data-title', this.settings.btn_change).addClass('selected');
		
		for (var i = 0; i < files.length; i++) {
			var filename = '', format = false;
			if(typeof files[i] === "string") filename = files[i];
			else if(hasFile && files[i] instanceof File) filename = $.trim( files[i].name );
			else if(files[i] instanceof Object && files[i].hasOwnProperty('name')) {
				//format & name specified by user (pre-displaying name, etc)
				filename = files[i].name;
				if(files[i].hasOwnProperty('type')) format = files[i].type;
				if(!files[i].hasOwnProperty('path')) files[i].path = files[i].name;
			}
			else continue;
			
			var index = filename.lastIndexOf("\\") + 1;
			if(index == 0)index = filename.lastIndexOf("/") + 1;
			filename = filename.substr(index);
			
			if(format == false) {
				if((/\.(jpe?g|png|gif|svg|bmp|tiff?)$/i).test(filename)) {				
					format = 'image';
				}
				else if((/\.(mpe?g|flv|mov|avi|swf|mp4|mkv|webm|wmv|3gp)$/i).test(filename)) {
					format = 'video';
				}
				else if((/\.(mp3|ogg|wav|wma|amr|aac)$/i).test(filename)) {
					format = 'audio';
				}
				else format = 'file';
			}
			
			var fileIcons = {
				'file' : 'fa fa-file',
				'image' : 'fa fa-picture-o file-image',
				'video' : 'fa fa-film file-video',
				'audio' : 'fa fa-music file-audio'
			};
			var fileIcon = fileIcons[format];
			
			
			if(!this.well_style) this.$label.find('.ace-file-name').attr({'data-title':filename}).find(ace.vars['.icon']).attr('class', ace.vars['icon'] + fileIcon);
			else {
				this.$label.append('<span class="ace-file-name" data-title="'+filename+'"><i class="'+ ace.vars['icon'] + fileIcon+'"></i></span>');
				var type = (inner_call === true && hasFile && files[i] instanceof File) ? $.trim(files[i].type) : '';
				var can_preview = hasFileReader && this.settings.thumbnail 
						&&
						( (type.length > 0 && type.match('image')) || (type.length == 0 && format == 'image') )//the second one is for older Android's default browser which gives an empty text for file.type
				if(can_preview) {
					var self = this;
					$.when(preview_image.call(this, files[i])).fail(function(result){
						//called on failure to load preview
						if(self.settings.preview_error) self.settings.preview_error.call(self, filename, result.code);
					})
				}
			}
		}
		
		return true;
	}
	
	Ace_File_Input.prototype.reset_input = function() {
	    this.reset_input_ui();
		this.reset_input_field();
	}
	
	Ace_File_Input.prototype.reset_input_ui = function() {
		 this.$label.attr({'data-title':this.settings.btn_choose, 'class':'ace-file-container'})
			.find('.ace-file-name:first').attr({'data-title':this.settings.no_file , 'class':'ace-file-name'})
			.find(ace.vars['.icon']).attr('class', ace.vars['icon'] + this.settings.no_icon)
			.prev('img').remove();
			if(!this.settings.no_icon) this.$label.find(ace.vars['.icon']).remove();
		
		this.$label.find('.ace-file-name').not(':first').remove();
		
		this.reset_input_data();
		
		//if(ace.vars['old_ie']) ace.helper.redraw(this.$container[0]);
	}
	Ace_File_Input.prototype.reset_input_field = function() {
		//http://stackoverflow.com/questions/1043957/clearing-input-type-file-using-jquery/13351234#13351234
		this.$element.wrap('<form>').parent().get(0).reset();
		this.$element.unwrap();
		
		//strangely when reset is called on this temporary inner form
		//only **IE9/10** trigger 'reset' on the outer form as well
		//and as we have mentioned to reset input on outer form reset
		//it causes infinite recusrsion by coming back to reset_input_field
		//thus calling reset again and again and again
		//so because when "reset" button of outer form is hit, file input is automatically reset
		//we just reset_input_ui to avoid recursion
	}
	Ace_File_Input.prototype.reset_input_data = function() {
		if(this.$element.data('ace_input_files')) {
			this.$element.removeData('ace_input_files');
			this.$element.removeData('ace_input_method');
		}
	}

	Ace_File_Input.prototype.enable_reset = function(can_reset) {
		this.can_reset = can_reset;
	}

	Ace_File_Input.prototype.disable = function() {
		this.disabled = true;
		this.$element.attr('disabled', 'disabled').addClass('disabled');
	}
	Ace_File_Input.prototype.enable = function() {
		this.disabled = false;
		this.$element.removeAttr('disabled').removeClass('disabled');
	}

	Ace_File_Input.prototype.files = function() {
		return $(this).data('ace_input_files') || null;
	}
	Ace_File_Input.prototype.method = function() {
		return $(this).data('ace_input_method') || '';
	}
	
	Ace_File_Input.prototype.update_settings = function(new_settings) {
		this.settings = $.extend({}, this.settings, new_settings);
		this.apply_settings();
	}
	
	Ace_File_Input.prototype.loading = function(is_loading) {
		if(is_loading === false) {
			this.$container.find('.ace-file-overlay').remove();
			this.element.removeAttribute('readonly');
		}
		else {
			var inside = typeof is_loading === 'string' ? is_loading : '<i class="overlay-content fa fa-spin fa-spinner orange2 fa-2x"></i>';
			var loader = this.$container.find('.ace-file-overlay');
			if(loader.length == 0) {
				loader = $('<div class="ace-file-overlay"></div>').appendTo(this.$container);
				loader.on('click tap', function(e) {
					e.stopImmediatePropagation();
					e.preventDefault();
					return false;
				});
				
				this.element.setAttribute('readonly' , 'true');//for IE
			}
			loader.empty().append(inside);
		}
	}



	var enable_drop_functionality = function() {
		var self = this;
		
		var dropbox = this.$element.parent();
		dropbox
		.off('dragenter')
		.on('dragenter', function(e){
			e.preventDefault();
			e.stopPropagation();
		})
		.off('dragover')
		.on('dragover', function(e){
			e.preventDefault();
			e.stopPropagation();
		})
		.off('drop')
		.on('drop', function(e){
			e.preventDefault();
			e.stopPropagation();

			if(self.disabled) return;
		
			var dt = e.originalEvent.dataTransfer;
			var file_list = dt.files;
			if(!self.multi && file_list.length > 1) {//single file upload, but dragged multiple files
				var tmpfiles = [];
				tmpfiles.push(file_list[0]);
				file_list = tmpfiles;//keep only first file
			}
			
			
			file_list = processFiles.call(self, file_list, true);//true means files have been selected, not dropped
			if(file_list === false) return false;

			self.$element.data('ace_input_method', 'drop');
			self.$element.data('ace_input_files', file_list);//save files data to be used later by user

			self.show_file_list(file_list , true);
			
			self.$element.triggerHandler('change' , [true]);//true means ace_inner_call
			return true;
		});
	}
	
	
	var handle_on_change = function() {
		var file_list = this.element.files || [this.element.value];/** make it an array */
		
		file_list = processFiles.call(this, file_list, false);//false means files have been selected, not dropped
		if(file_list === false) return false;
		
		this.$element.data('ace_input_method', 'select');
		this.$element.data('ace_input_files', file_list);
		
		this.show_file_list(file_list , true);
		
		return true;
	}



	var preview_image = function(file) {
		var self = this;
		var $span = self.$label.find('.ace-file-name:last');//it should be out of onload, otherwise all onloads may target the same span because of delays
		
		var deferred = new $.Deferred;
		
		var getImage = function(src) {
			$span.prepend("<img class='middle' style='display:none;' />");
			var img = $span.find('img:last').get(0);
		
			$(img).one('load', function() {
				imgLoaded.call(null, img);
			}).one('error', function() {
				imgFailed.call(null, img);
			});

			img.src = src;
		}
		var imgLoaded = function(img) {
			//if image loaded successfully
			var size = 50;
			if(self.settings.thumbnail == 'large') size = 150;
			else if(self.settings.thumbnail == 'fit') size = $span.width();
			$span.addClass(size > 50 ? 'large' : '');

			var thumb = get_thumbnail(img, size/**, file.type*/);
			if(thumb == null) {
				//if making thumbnail fails
				$(this).remove();
				deferred.reject({code:Ace_File_Input.error['THUMBNAIL_FAILED']});
				return;
			}

			var w = thumb.w, h = thumb.h;
			if(self.settings.thumbnail == 'small') {w=h=size;};
			$(img).css({'background-image':'url('+thumb.src+')' , width:w, height:h})
					.data('thumb', thumb.src)
					.attr({src:'data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAEAAAABCAYAAAAfFcSJAAAADUlEQVQImWNgYGBgAAAABQABh6FO1AAAAABJRU5ErkJggg=='})
					.show()

			///////////////////
			deferred.resolve();
		}
		var imgFailed = function(img) {
			//for example when a file has image extenstion, but format is something else
			$span.find('img').remove();
			deferred.reject({code:Ace_File_Input.error['IMAGE_LOAD_FAILED']});
		}
		
		if(hasFile && file instanceof File) {
			var reader = new FileReader();
			reader.onload = function (e) {
				getImage(e.target.result);
			}
			reader.onerror = function (e) {
				deferred.reject({code:Ace_File_Input.error['FILE_LOAD_FAILED']});
			}
			reader.readAsDataURL(file);
		}
		else {
			if(file instanceof Object && file.hasOwnProperty('path')) {
				getImage(file.path);//file is a file name (path) --- this is used to pre-show user-selected image
			}
		}
		
		return deferred.promise();
	}

	var get_thumbnail = function(img, size, type) {
		var w = img.width, h = img.height;
		
		//**IE10** is not giving correct width using img.width so we use $(img).width()
		w = w > 0 ? w : $(img).width()
		h = h > 0 ? h : $(img).height()

		if(w > size || h > size) {
		  if(w > h) {
			h = parseInt(size/w * h);
			w = size;
		  } else {
			w = parseInt(size/h * w);
			h = size;
		  }
		}


		var dataURL
		try {
			var canvas = document.createElement('canvas');
			canvas.width = w; canvas.height = h;
			var context = canvas.getContext('2d');
			context.drawImage(img, 0, 0, img.width, img.height, 0, 0, w, h);
			dataURL = canvas.toDataURL(/*type == 'image/jpeg' ? type : 'image/png', 10*/)
		} catch(e) {
			dataURL = null;
		}
		if(! dataURL) return null;
		

		//there was only one image that failed in firefox completely randomly! so let's double check things
		if( !( /^data\:image\/(png|jpe?g|gif);base64,[0-9A-Za-z\+\/\=]+$/.test(dataURL)) ) dataURL = null;
		if(! dataURL) return null;
		

		return {src: dataURL, w:w, h:h};
	}
	

	
	var processFiles = function(file_list, dropped) {
		var ret = checkFileList.call(this, file_list, dropped);
		if(ret === -1) {
			this.reset_input();
			return false;
		}
		if( !ret || ret.length == 0 ) {
			if( !this.$element.data('ace_input_files') ) this.reset_input();
			//if nothing selected before, reset because of the newly unacceptable (ret=false||length=0) selection
			//otherwise leave the previous selection intact?!!!
			return false;
		}
		if (ret instanceof Array || (hasFileList && ret instanceof FileList)) file_list = ret;
		
		
		ret = true;
		if(this.settings.before_change) ret = this.settings.before_change.call(this.element, file_list, dropped);
		if(ret === -1) {
			this.reset_input();
			return false;
		}
		if(!ret || ret.length == 0) {
			if( !this.$element.data('ace_input_files') ) this.reset_input();
			return false;
		}
		
		//inside before_change you can return a modified File Array as result
		if (ret instanceof Array || (hasFileList && ret instanceof FileList)) file_list = ret;
		
		return file_list;
	}
	
	
	var getExtRegex = function(ext) {
		if(!ext) return null;
		if(typeof ext === 'string') ext = [ext];
		if(ext.length == 0) return null;
		return new RegExp("\.(?:"+ext.join('|')+")$", "i");
	}
	var getMimeRegex = function(mime) {
		if(!mime) return null;
		if(typeof mime === 'string') mime = [mime];
		if(mime.length == 0) return null;
		return new RegExp("^(?:"+mime.join('|').replace(/\//g, "\\/")+")$", "i");
	}
	var checkFileList = function(files, dropped) {
		var allowExt   = getExtRegex(this.settings.allowExt);

		var denyExt    = getExtRegex(this.settings.denyExt);
		
		var allowMime  = getMimeRegex(this.settings.allowMime);

		var denyMime   = getMimeRegex(this.settings.denyMime);

		var maxSize    = this.settings.maxSize || false;
		
		if( !(allowExt || denyExt || allowMime || denyMime || maxSize) ) return true;//no checking required


		var safe_files = [];
		var error_list = {}
		for(var f = 0; f < files.length; f++) {
			var file = files[f];
			
			//file is either a string(file name) or a File object
			var filename = !hasFile ? file : file.name;
			if( allowExt && !allowExt.test(filename) ) {
				//extension not matching whitelist, so drop it
				if(!('ext' in error_list)) error_list['ext'] = [];
				 error_list['ext'].push(filename);
				
				continue;
			} else if( denyExt && denyExt.test(filename) ) {
				//extension is matching blacklist, so drop it
				if(!('ext' in error_list)) error_list['ext'] = [];
				 error_list['ext'].push(filename);
				
				continue;
			}

			var type;
			if( !hasFile ) {
				//in browsers that don't support FileReader API
				safe_files.push(file);
				continue;
			}
			else if((type = $.trim(file.type)).length > 0) {
				//there is a mimetype for file so let's check against are rules
				if( allowMime && !allowMime.test(type) ) {
					//mimeType is not matching whitelist, so drop it
					if(!('mime' in error_list)) error_list['mime'] = [];
					 error_list['mime'].push(filename);
					continue;
				}
				else if( denyMime && denyMime.test(type) ) {
					//mimeType is matching blacklist, so drop it
					if(!('mime' in error_list)) error_list['mime'] = [];
					 error_list['mime'].push(filename);
					continue;
				}
			}

			if( maxSize && file.size > maxSize ) {
				//file size is not acceptable
				if(!('size' in error_list)) error_list['size'] = [];
				 error_list['size'].push(filename);
				continue;
			}

			safe_files.push(file)
		}
		
	
		
		if(safe_files.length == files.length) return files;//return original file list if all are valid

		/////////
		var error_count = {'ext': 0, 'mime': 0, 'size': 0}
		if( 'ext' in error_list ) error_count['ext'] = error_list['ext'].length;
		if( 'mime' in error_list ) error_count['mime'] = error_list['mime'].length;
		if( 'size' in error_list ) error_count['size'] = error_list['size'].length;
		
		var event
		this.$element.trigger(
			event = new $.Event('file.error.ace'), 
			{
				'file_count': files.length,
				'invalid_count' : files.length - safe_files.length,
				'error_list' : error_list,
				'error_count' : error_count,
				'dropped': dropped
			}
		);
		if ( event.isDefaultPrevented() ) return -1;//it will reset input
		//////////

		return safe_files;//return safe_files
	}



	///////////////////////////////////////////
	$.fn.aceFileInput = $.fn.ace_file_input = function (option,value) {
		var retval;

		var $set = this.each(function () {
			var $this = $(this);
			var data = $this.data('ace_file_input');
			var options = typeof option === 'object' && option;

			if (!data) $this.data('ace_file_input', (data = new Ace_File_Input(this, options)));
			if (typeof option === 'string') retval = data[option](value);
		});

		return (retval === undefined) ? $set : retval;
	};


	$.fn.ace_file_input.defaults = {
		style: false,
		no_file: 'No File ...',
		no_icon: 'fa fa-upload',
		btn_choose: 'Choose',
		btn_change: 'Change',
		icon_remove: 'fa fa-times',
		droppable: false,
		thumbnail: false,//large, fit, small
		
		allowExt: null,
		denyExt: null,
		allowMime: null,
		denyMime: null,
		maxSize: false,
		
		//callbacks
		before_change: null,
		before_remove: null,
		preview_error: null
     }


})(window.jQuery);
/**
  <b>Bootstrap 2 typeahead plugin.</b> With Bootstrap <u>3</u> it's been dropped in favor of a more advanced separate plugin.
  Pretty good for simple cases such as autocomplete feature of the search box and required for <u class="text-danger">Tag input</u> plugin.
*/

/* =============================================================
 * bootstrap-typeahead.js v2.3.2
 * http://twitter.github.com/bootstrap/javascript.html#typeahead
 * =============================================================
 * Copyright 2012 Twitter, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * ============================================================ */


!function($){

  "use strict"; // jshint ;_;


 /* TYPEAHEAD PUBLIC CLASS DEFINITION
  * ================================= */

  var Typeahead = function (element, options) {
    this.$element = $(element)
    this.options = $.extend({}, $.fn.bs_typeahead.defaults, options)
    this.matcher = this.options.matcher || this.matcher
    this.sorter = this.options.sorter || this.sorter
    this.highlighter = this.options.highlighter || this.highlighter
    this.updater = this.options.updater || this.updater
    this.source = this.options.source
    this.$menu = $(this.options.menu)
    this.shown = false
    this.listen()
  }

  Typeahead.prototype = {

    constructor: Typeahead

  , select: function () {
      var val = this.$menu.find('.active').attr('data-value')
      this.$element
        .val(this.updater(val))
        .change()
      return this.hide()
    }

  , updater: function (item) {
      return item
    }

  , show: function () {
      var pos = $.extend({}, this.$element.position(), {
        height: this.$element[0].offsetHeight
      })

      this.$menu
        .insertAfter(this.$element)
        .css({
          top: pos.top + pos.height
        , left: pos.left
        })
        .show()

      this.shown = true
      return this
    }

  , hide: function () {
      this.$menu.hide()
      this.shown = false
      return this
    }

  , lookup: function (event) {
      var items

      this.query = this.$element.val()

      if (!this.query || this.query.length < this.options.minLength) {
        return this.shown ? this.hide() : this
      }

      items = $.isFunction(this.source) ? this.source(this.query, $.proxy(this.process, this)) : this.source

      return items ? this.process(items) : this
    }

  , process: function (items) {
      var that = this

      items = $.grep(items, function (item) {
        return that.matcher(item)
      })

      items = this.sorter(items)

      if (!items.length) {
        return this.shown ? this.hide() : this
      }

      return this.render(items.slice(0, this.options.items)).show()
    }

  , matcher: function (item) {
      return ~item.toLowerCase().indexOf(this.query.toLowerCase())
    }

  , sorter: function (items) {
      var beginswith = []
        , caseSensitive = []
        , caseInsensitive = []
        , item

      while (item = items.shift()) {
        if (!item.toLowerCase().indexOf(this.query.toLowerCase())) beginswith.push(item)
        else if (~item.indexOf(this.query)) caseSensitive.push(item)
        else caseInsensitive.push(item)
      }

      return beginswith.concat(caseSensitive, caseInsensitive)
    }

  , highlighter: function (item) {
      var query = this.query.replace(/[\-\[\]{}()*+?.,\\\^$|#\s]/g, '\\$&')
      return item.replace(new RegExp('(' + query + ')', 'ig'), function ($1, match) {
        return '<strong>' + match + '</strong>'
      })
    }

  , render: function (items) {
      var that = this

      items = $(items).map(function (i, item) {
        i = $(that.options.item).attr('data-value', item)
        i.find('a').html(that.highlighter(item))
        return i[0]
      })

      items.first().addClass('active')
      this.$menu.html(items)
      return this
    }

  , next: function (event) {
      var active = this.$menu.find('.active').removeClass('active')
        , next = active.next()

      if (!next.length) {
        next = $(this.$menu.find('li')[0])
      }

      next.addClass('active')
    }

  , prev: function (event) {
      var active = this.$menu.find('.active').removeClass('active')
        , prev = active.prev()

      if (!prev.length) {
        prev = this.$menu.find('li').last()
      }

      prev.addClass('active')
    }

  , listen: function () {
      this.$element
        .on('focus',    $.proxy(this.focus, this))
        .on('blur',     $.proxy(this.blur, this))
        .on('keypress', $.proxy(this.keypress, this))
        .on('keyup',    $.proxy(this.keyup, this))

      if (this.eventSupported('keydown')) {
        this.$element.on('keydown', $.proxy(this.keydown, this))
      }

      this.$menu
        .on('click', $.proxy(this.click, this))
        .on('mouseenter', 'li', $.proxy(this.mouseenter, this))
        .on('mouseleave', 'li', $.proxy(this.mouseleave, this))
    }

  , eventSupported: function(eventName) {
      var isSupported = eventName in this.$element
      if (!isSupported) {
        this.$element.setAttribute(eventName, 'return;')
        isSupported = typeof this.$element[eventName] === 'function'
      }
      return isSupported
    }

  , move: function (e) {
      if (!this.shown) return

      switch(e.keyCode) {
        case 9: // tab
        case 13: // enter
        case 27: // escape
          e.preventDefault()
          break

        case 38: // up arrow
          e.preventDefault()
          this.prev()
          break

        case 40: // down arrow
          e.preventDefault()
          this.next()
          break
      }

      e.stopPropagation()
    }

  , keydown: function (e) {
      this.suppressKeyPressRepeat = ~$.inArray(e.keyCode, [40,38,9,13,27])
      this.move(e)
    }

  , keypress: function (e) {
      if (this.suppressKeyPressRepeat) return
      this.move(e)
    }

  , keyup: function (e) {
      switch(e.keyCode) {
        case 40: // down arrow
        case 38: // up arrow
        case 16: // shift
        case 17: // ctrl
        case 18: // alt
          break

        case 9: // tab
        case 13: // enter
          if (!this.shown) return
          this.select()
          break

        case 27: // escape
          if (!this.shown) return
          this.hide()
          break

        default:
          this.lookup()
      }

      e.stopPropagation()
      e.preventDefault()
  }

  , focus: function (e) {
      this.focused = true
    }

  , blur: function (e) {
      this.focused = false
      if (!this.mousedover && this.shown) this.hide()
    }

  , click: function (e) {
      e.stopPropagation()
      e.preventDefault()
      this.select()
      this.$element.focus()
    }

  , mouseenter: function (e) {
      this.mousedover = true
      this.$menu.find('.active').removeClass('active')
      $(e.currentTarget).addClass('active')
    }

  , mouseleave: function (e) {
      this.mousedover = false
      if (!this.focused && this.shown) this.hide()
    }

  }


  /* TYPEAHEAD PLUGIN DEFINITION
   * =========================== */

  var old = $.fn.bs_typeahead

  $.fn.bs_typeahead = function (option) {
    return this.each(function () {
      var $this = $(this)
        , data = $this.data('bs_typeahead')
        , options = typeof option == 'object' && option
      if (!data) $this.data('bs_typeahead', (data = new Typeahead(this, options)))
      if (typeof option == 'string') data[option]()
    })
  }

  $.fn.bs_typeahead.defaults = {
    source: []
  , items: 8
  , menu: '<ul class="typeahead dropdown-menu"></ul>'
  , item: '<li><a href="#"></a></li>'
  , minLength: 1
  }

  $.fn.bs_typeahead.Constructor = Typeahead


 /* TYPEAHEAD NO CONFLICT
  * =================== */

  $.fn.bs_typeahead.noConflict = function () {
    $.fn.bs_typeahead = old
    return this
  }


 /* TYPEAHEAD DATA-API
  * ================== */

  $(document).on('focus.bs_typeahead.data-api', '[data-provide="bs_typeahead"]', function (e) {
    var $this = $(this)
    if ($this.data('bs_typeahead')) return
    $this.bs_typeahead($this.data())
  })

}(window.jQuery);/**
 <b>Wysiwyg</b>. A wrapper for Bootstrap wyswiwyg plugin.
 It's just a wrapper so you still need to include Bootstrap wysiwyg script first.
*/
(function($ , undefined) {
	$.fn.ace_wysiwyg = function($options , undefined) {
		var options = $.extend( {
			speech_button:true,
			wysiwyg:{}
        }, $options);

		var color_values = [
			'#ac725e','#d06b64','#f83a22','#fa573c','#ff7537','#ffad46',
			'#42d692','#16a765','#7bd148','#b3dc6c','#fbe983','#fad165',
			'#92e1c0','#9fe1e7','#9fc6e7','#4986e7','#9a9cff','#b99aff',
			'#c2c2c2','#cabdbf','#cca6ac','#f691b2','#cd74e6','#a47ae2',
			'#444444'
		]

		var button_defaults =
		{
			'font' : {
				values:['Arial', 'Courier', 'Comic Sans MS', 'Helvetica', 'Open Sans', 'Tahoma', 'Verdana'],
				icon:'fa fa-font',
				title:'Font'
			},
			'fontSize' : {
				values:{5:'Huge', 3:'Normal', 1:'Small'},
				icon:'fa fa-text-height',
				title:'Font Size'
			},
			'bold' : {
				icon : 'fa fa-bold',
				title : 'Bold (Ctrl/Cmd+B)'
			},
			'italic' : {
				icon : 'fa fa-italic',
				title : 'Italic (Ctrl/Cmd+I)'
			},
			'strikethrough' : {
				icon : 'fa fa-strikethrough',
				title : 'Strikethrough'
			},
			'underline' : {
				icon : 'fa fa-underline',
				title : 'Underline'
			},
			'insertunorderedlist' : {
				icon : 'fa fa-list-ul',
				title : 'Bullet list'
			},
			'insertorderedlist' : {
				icon : 'fa fa-list-ol',
				title : 'Number list'
			},
			'outdent' : {
				icon : 'fa fa-outdent',
				title : 'Reduce indent (Shift+Tab)'
			},
			'indent' : {
				icon : 'fa fa-indent',
				title : 'Indent (Tab)'
			},
			'justifyleft' : {
				icon : 'fa fa-align-left',
				title : 'Align Left (Ctrl/Cmd+L)'
			},
			'justifycenter' : {
				icon : 'fa fa-align-center',
				title : 'Center (Ctrl/Cmd+E)'
			},
			'justifyright' : {
				icon : 'fa fa-align-right',
				title : 'Align Right (Ctrl/Cmd+R)'
			},
			'justifyfull' : {
				icon : 'fa fa-align-justify',
				title : 'Justify (Ctrl/Cmd+J)'
			},
			'createLink' : {
				icon : 'fa fa-link',
				title : 'Hyperlink',
				button_text : 'Add',
				placeholder : 'URL',
				button_class : 'btn-primary'
			},
			'unlink' : {
				icon : 'fa fa-chain-broken',
				title : 'Remove Hyperlink'
			},
			'insertImage' : {
				icon : 'fa fa-picture-o',
				title : 'Insert picture',
				button_text : '<i class="'+ ace.vars['icon'] + 'fa fa-file"></i> Choose Image &hellip;',
				placeholder : 'Image URL',
				button_insert : 'Insert',
				button_class : 'btn-success',
				button_insert_class : 'btn-primary',
				choose_file: true //show the choose file button?
			},
			'foreColor' : {
				values : color_values,
				title : 'Change Color'
			},
			'backColor' : {
				values : color_values,
				title : 'Change Background Color'
			},
			'undo' : {
				icon : 'fa fa-undo',
				title : 'Undo (Ctrl/Cmd+Z)'
			},
			'redo' : {
				icon : 'fa fa-repeat',
				title : 'Redo (Ctrl/Cmd+Y)'
			},
			'viewSource' : {
				icon : 'fa fa-code',
				title : 'View Source'
			}
		}
		
		var toolbar_buttons =
		options.toolbar ||
		[
			'font',
			null,
			'fontSize',
			null,
			'bold',
			'italic',
			'strikethrough',
			'underline',
			null,
			'insertunorderedlist',
			'insertorderedlist',
			'outdent',
			'indent',
			null,
			'justifyleft',
			'justifycenter',
			'justifyright',
			'justifyfull',
			null,
			'createLink',
			'unlink',
			null,
			'insertImage',
			null,
			'foreColor',
			null,
			'undo',
			'redo',
			null,
			'viewSource'
		]


		this.each(function() {
			var toolbar = ' <div class="wysiwyg-toolbar btn-toolbar center"> <div class="btn-group"> ';

			for(var tb in toolbar_buttons) if(toolbar_buttons.hasOwnProperty(tb)) {
				var button = toolbar_buttons[tb];
				if(button === null){
					toolbar += ' </div> <div class="btn-group"> ';
					continue;
				}
				
				if(typeof button == "string" && button in button_defaults) {
					button = button_defaults[button];
					button.name = toolbar_buttons[tb];
				} else if(typeof button == "object" && button.name in button_defaults) {
					button = $.extend(button_defaults[button.name] , button);
				}
				else continue;
				
				var className = "className" in button ? button.className : 'btn-default';
				switch(button.name) {
					case 'font':
						toolbar += ' <a class="btn btn-sm '+className+' dropdown-toggle" data-toggle="dropdown" title="'+button.title+'"><i class="'+ ace.vars['icon'] + button.icon+'"></i><i class="' + ace.vars['icon'] + 'fa fa-angle-down icon-on-right"></i></a> ';
						toolbar += ' <ul class="dropdown-menu dropdown-light dropdown-caret">';
						for(var font in button.values)
							if(button.values.hasOwnProperty(font))
								toolbar += ' <li><a data-edit="fontName ' + button.values[font] +'" style="font-family:\''+ button.values[font]  +'\'">'+button.values[font]  + '</a></li> '
						toolbar += ' </ul>';
					break;

					case 'fontSize':
						toolbar += ' <a class="btn btn-sm '+className+' dropdown-toggle" data-toggle="dropdown" title="'+button.title+'"><i class="'+ ace.vars['icon'] + button.icon+'"></i>&nbsp;<i class="'+ ace.vars['icon'] + 'fa fa-angle-down icon-on-right"></i></a> ';
						toolbar += ' <ul class="dropdown-menu dropdown-light dropdown-caret"> ';
						for(var size in button.values)
							if(button.values.hasOwnProperty(size))
								toolbar += ' <li><a data-edit="fontSize '+size+'"><font size="'+size+'">'+ button.values[size] +'</font></a></li> '
						toolbar += ' </ul> ';
					break;

					case 'createLink':
						toolbar += ' <div class="btn-group"> <a class="btn btn-sm '+className+' dropdown-toggle" data-toggle="dropdown" title="'+button.title+'"><i class="'+ ace.vars['icon'] + button.icon+'"></i></a> ';
						toolbar += ' <div class="dropdown-menu dropdown-caret dropdown-menu-right">\
							 <div class="input-group">\
								<input class="form-control" placeholder="'+button.placeholder+'" type="text" data-edit="'+button.name+'" />\
								<span class="input-group-btn">\
									<button class="btn btn-sm '+button.button_class+'" type="button">'+button.button_text+'</button>\
								</span>\
							 </div>\
						</div> </div>';
					break;

					case 'insertImage':
						toolbar += ' <div class="btn-group"> <a class="btn btn-sm '+className+' dropdown-toggle" data-toggle="dropdown" title="'+button.title+'"><i class="'+ ace.vars['icon'] + button.icon+'"></i></a> ';
						toolbar += ' <div class="dropdown-menu dropdown-caret dropdown-menu-right">\
							 <div class="input-group">\
								<input class="form-control" placeholder="'+button.placeholder+'" type="text" data-edit="'+button.name+'" />\
								<span class="input-group-btn">\
									<button class="btn btn-sm '+button.button_insert_class+'" type="button">'+button.button_insert+'</button>\
								</span>\
							 </div>';
							if( button.choose_file && 'FileReader' in window ) toolbar +=
							 '<div class="space-2"></div>\
							 <label class="center block no-margin-bottom">\
								<button class="btn btn-sm '+button.button_class+' wysiwyg-choose-file" type="button">'+button.button_text+'</button>\
								<input type="file" data-edit="'+button.name+'" />\
							  </label>'
						toolbar += ' </div> </div>';
					break;

					case 'foreColor':
					case 'backColor':
						toolbar += ' <select class="hide wysiwyg_colorpicker" title="'+button.title+'"> ';
						$.each(button.values, function (_, color) {
                            toolbar += ' <option value="' + color + '">' + color + '</option> ';
                        });
						toolbar += ' </select> ';
						toolbar += ' <input style="display:none;" disabled class="hide" type="text" data-edit="'+button.name+'" /> ';
					break;

					case 'viewSource':
						toolbar += ' <a class="btn btn-sm '+className+'" data-view="source" title="'+button.title+'"><i class="'+ ace.vars['icon'] + button.icon+'"></i></a> ';
					break;
					default:
						toolbar += ' <a class="btn btn-sm '+className+'" data-edit="'+button.name+'" title="'+button.title+'"><i class="'+ ace.vars['icon'] + button.icon+'"></i></a> ';
					break;
				}
			}
			toolbar += ' </div> ';
			////////////
			var speech_input;
			if (options.speech_button && 'onwebkitspeechchange' in (speech_input = document.createElement('input'))) {
				toolbar += ' <input class="wysiwyg-speech-input" type="text" data-edit="inserttext" x-webkit-speech />';
			}
			speech_input = null;
			////////////
			toolbar += ' </div> ';


			//if we have a function to decide where to put the toolbar, then call that
			if(options.toolbar_place) toolbar = options.toolbar_place.call(this, toolbar);
			//otherwise put it just before our DIV
			else toolbar = $(this).before(toolbar).prev();

			toolbar.find('a[title]').tooltip({animation:false, container:'body'});
			toolbar.find('.dropdown-menu input[type=text]').on('click', function() {return false})
		    .on('change', function() {$(this).closest('.dropdown-menu').siblings('.dropdown-toggle').dropdown('toggle')})
			.on('keydown', function (e) {
				if(e.which == 27) {
					this.value = '';
					$(this).change();
				}
				else if(e.which == 13) {
					e.preventDefault();
					e.stopPropagation();
					$(this).change();
				}
			});
			
			toolbar.find('input[type=file]').prev().on(ace.click_event, function (e) { 
				$(this).next().click();
			});
			toolbar.find('.wysiwyg_colorpicker').each(function() {
				$(this).ace_colorpicker({pull_right:true}).change(function(){
					$(this).nextAll('input').eq(0).val(this.value).change();
				}).next().find('.btn-colorpicker').tooltip({title: this.title, animation:false, container:'body'})
			});
			
			
			var self = $(this);
			//view source
			var view_source = false;
			toolbar.find('a[data-view=source]').on('click', function(e){
				e.preventDefault();
				
				if(!view_source) {
					$('<textarea />')
					.css({'width':self.outerWidth(), 'height':self.outerHeight()})
					.val(self.html())
					.insertAfter(self)
					self.hide();
					
					$(this).addClass('active');
				}
				else {
					var textarea = self.next();
					self.html(textarea.val()).show();
					textarea.remove();
					
					$(this).removeClass('active');
				}
				
				view_source = !view_source;
			});


			var $options = $.extend({}, { activeToolbarClass: 'active' , toolbarSelector : toolbar }, options.wysiwyg || {})
			$(this).wysiwyg( $options );
		});

		return this;
	}


})(window.jQuery);

/**
 <b>Spinner</b>. A wrapper for FuelUX spinner element.
 It's just a wrapper so you still need to include FuelUX spinner script first.
*/
(function($ , undefined) {
	//a wrapper for fuelux spinner
	function Ace_Spinner(element , _options) {
		var attrib_values = ace.helper.getAttrSettings(element, $.fn.ace_spinner.defaults);
		var options = $.extend({}, $.fn.ace_spinner.defaults, _options, attrib_values);
	
		var max = options.max
		max = (''+max).length
		var width = parseInt(Math.max((max * 20 + 40) , 90))

		var $element = $(element);
		
		var btn_class = 'btn-sm';//default
		var sizing = 2;
		if($element.hasClass('input-sm')) {
			btn_class = 'btn-xs';
			sizing = 1;
		}
		else if($element.hasClass('input-lg')) {
			btn_class = 'btn-lg';
			sizing = 3;
		}
		
		if(sizing == 2) width += 25;
		else if(sizing == 3) width += 50;
		
		$element.addClass('spinbox-input form-control text-center').wrap('<div class="ace-spinner middle">')

		var $parent_div = $element.closest('.ace-spinner').spinbox(options).wrapInner("<div class='input-group'></div>")
		var $spinner = $parent_div.data('fu.spinbox');
		
		if(options.on_sides)
		{
			$element
			.before('<div class="spinbox-buttons input-group-btn">\
					<button type="button" class="btn spinbox-down '+btn_class+' '+options.btn_down_class+'">\
						<i class="icon-only '+ ace.vars['icon'] + options.icon_down+'"></i>\
					</button>\
				</div>')
			.after('<div class="spinbox-buttons input-group-btn">\
					<button type="button" class="btn spinbox-up '+btn_class+' '+options.btn_up_class+'">\
						<i class="icon-only '+ ace.vars['icon'] + options.icon_up+'"></i>\
					</button>\
				</div>');

			$parent_div.addClass('touch-spinner')
			$parent_div.css('width' , width+'px')
		}
		else {
			 $element
			 .after('<div class="spinbox-buttons input-group-btn">\
					<button type="button" class="btn spinbox-up '+btn_class+' '+options.btn_up_class+'">\
						<i class="icon-only '+ ace.vars['icon'] + options.icon_up+'"></i>\
					</button>\
					<button type="button" class="btn spinbox-down '+btn_class+' '+options.btn_down_class+'">\
						<i class="icon-only '+ ace.vars['icon'] + options.icon_down+'"></i>\
					</button>\
				</div>')

			if(ace.vars['touch'] || options.touch_spinner) {
				$parent_div.addClass('touch-spinner')
				$parent_div.css('width' , width+'px')
			}
			else {
				$element.next().addClass('btn-group-vertical');
				$parent_div.css('width' , width+'px')
			}
		}

		$parent_div.on('changed', function(){
			$element.trigger('change')//trigger the input's change event
		});

		this._call = function(name, arg) {
			$spinner[name](arg);
		}
	}


	$.fn.ace_spinner = function(option, value) {
		var retval;

		var $set = this.each(function() {
			var $this = $(this);
			var data = $this.data('ace_spinner');
			var options = typeof option === 'object' && option;

			if (!data) {
				options = $.extend({}, $.fn.ace_spinner.defaults, option);
				$this.data('ace_spinner', (data = new Ace_Spinner(this, options)));
			}
			if (typeof option === 'string') retval = data._call(option, value);
		});

		return (retval === undefined) ? $set : retval;
	}
	
	$.fn.ace_spinner.defaults = {
		'icon_up' : 'fa fa-chevron-up',
		'icon_down': 'fa fa-chevron-down',
		
		'on_sides': false,		
		'btn_up_class': '',
		'btn_down_class' : '',
		
		'max' : 999,
		'touch_spinner': false
     }


})(window.jQuery);
/**
 <b>Wizard</b>. A wrapper for FuelUX wizard element.
 It's just a wrapper so you still need to include FuelUX wizard script first.
*/
(function($ , undefined) {
	$.fn.aceWizard = $.fn.ace_wizard = function(options) {

		this.each(function() {
			var $this = $(this);
			$this.wizard();
			
			if(ace.vars['old_ie']) $this.find('ul.steps > li').last().addClass('last-child');

			var buttons = (options && options['buttons']) ? $(options['buttons']) : $this.siblings('.wizard-actions').eq(0);
			var $wizard = $this.data('fu.wizard');
			$wizard.$prevBtn.remove();
			$wizard.$nextBtn.remove();
			
			$wizard.$prevBtn = buttons.find('.btn-prev').eq(0).on(ace.click_event,  function(){
				$wizard.previous();
			}).attr('disabled', 'disabled');
			$wizard.$nextBtn = buttons.find('.btn-next').eq(0).on(ace.click_event,  function(){
				$wizard.next();
			}).removeAttr('disabled');
			$wizard.nextText = $wizard.$nextBtn.text();
			
			var step = options && ((options.selectedItem && options.selectedItem.step) || options.step);
			if(step) {
				$wizard.currentStep = step;
				$wizard.setState();
			}
		});

		return this;
	}

})(window.jQuery);
/**
 <b>Content Slider</b>. with custom content and elements based on Bootstrap modals.
*/
(function($ , undefined) {
	var $window = $(window);

	function Aside(modal, settings) {
		var self = this;
	
		var $modal = $(modal);
		var placement = 'right', vertical = false;
		var hasFade = $modal.hasClass('fade');//Bootstrap enables transition only when modal is ".fade"

		var attrib_values = ace.helper.getAttrSettings(modal, $.fn.ace_aside.defaults);
		this.settings = $.extend({}, $.fn.ace_aside.defaults, settings, attrib_values);
		
		//if no scroll style specified and modal has dark background, let's make scrollbars 'white'
		if(this.settings.background && !settings.scroll_style && !attrib_values.scroll_style) { 
			this.settings.scroll_style = 'scroll-white no-track';
		}

		
		this.container = this.settings.container;
		if(this.container) {
			try {
				if( $(this.container).get(0) == document.body ) this.container = null;
			} catch(e) {}
		}
		if(this.container) {
			this.settings.backdrop = false;//no backdrop when inside another element?
			$modal.addClass('aside-contained');
		}

		
		var dialog = $modal.find('.modal-dialog');
		var content = $modal.find('.modal-content');
		var delay = 300;
		
		this.initiate = function() {
			modal.className = modal.className.replace(/(\s|^)aside\-(right|top|left|bottom)(\s|$)/ig , '$1$3');

			placement = this.settings.placement;
			if(placement) placement = $.trim(placement.toLowerCase());
			if(!placement || !(/right|top|left|bottom/.test(placement))) placement = 'right';

			$modal.attr('data-placement', placement);
			$modal.addClass('aside-' + placement);
			
			if( /right|left/.test(placement) ) {
				vertical = true;
				$modal.addClass('aside-vc');//vertical
			}
			else $modal.addClass('aside-hz');//horizontal
			
			if( this.settings.fixed ) $modal.addClass('aside-fixed');
			if( this.settings.background ) $modal.addClass('aside-dark');
			if( this.settings.offset ) $modal.addClass('navbar-offset');
			
			if( !this.settings.transition ) $modal.addClass('transition-off');
			
			$modal.addClass('aside-hidden');

			this.insideContainer();
			
			/////////////////////////////
			
			dialog = $modal.find('.modal-dialog');
			content = $modal.find('.modal-content');
			
			if(!this.settings.body_scroll) {
				//don't allow body scroll when modal is open
				$modal.on('mousewheel.aside DOMMouseScroll.aside touchmove.aside pointermove.aside', function(e) {
					if( !$.contains(content[0], e.target) ) {
						e.preventDefault();
						return false;
					}
				})
			}
			
			if( this.settings.backdrop == false ) {
				$modal.addClass('no-backdrop');
			}
		}
		
		
		this.show = function() {
			if(this.settings.backdrop == false) {
			  try {
				$modal.data('bs.modal').$backdrop.remove();
			  } catch(e){}
			}
	
			if(this.container) $(this.container).addClass('overflow-hidden');
			else $modal.css('position', 'fixed')
			
			$modal.removeClass('aside-hidden');
		}
		
		this.hide = function() {
			if(this.container) {
				this.container.addClass('overflow-hidden');
				
				if(ace.vars['firefox']) {
					//firefox needs a bit of forcing re-calculation
					modal.offsetHeight;
				}
			}
		
			toggleButton();
			
			if(ace.vars['transition'] && !hasFade) {
				$modal.one('bsTransitionEnd', function() {
					$modal.addClass('aside-hidden');
					$modal.css('position', '');
					
					if(self.container) self.container.removeClass('overflow-hidden');
				}).emulateTransitionEnd(delay);
			}
		}
		
		this.shown = function() {
			toggleButton();
			$('body').removeClass('modal-open').css('padding-right', '');
			
			if( this.settings.backdrop == 'invisible' ) {
			  try {
				$modal.data('bs.modal').$backdrop.css('opacity', 0);
			  } catch(e){}
			}

			var size = !vertical ? dialog.height() : content.height();
			if(!ace.vars['touch']) {
				if(!content.hasClass('ace-scroll')) {
					content.ace_scroll({
							size: size,
							reset: true,
							mouseWheelLock: true,
							lockAnyway: !this.settings.body_scroll,
							styleClass: this.settings.scroll_style,
							'observeContent': true,
							'hideOnIdle': !ace.vars['old_ie'],
							'hideDelay': 1500
					})
				}
			}
			else {
				content.addClass('overflow-scroll').css('max-height', size+'px');
			}

			$window
			.off('resize.modal.aside')
			.on('resize.modal.aside', function() {
				if(!ace.vars['touch']) {
				  content.ace_scroll('disable');//to get correct size when going from small window size to large size
					var size = !vertical ? dialog.height() : content.height();
					content
					.ace_scroll('update', {'size': size})
					.ace_scroll('enable')
					.ace_scroll('reset');
				}
				else content.css('max-height', (!vertical ? dialog.height() : content.height())+'px');
			}).triggerHandler('resize.modal.aside');
			
			
			///////////////////////////////////////////////////////////////////////////
			if(self.container && ace.vars['transition'] && !hasFade) {
				$modal.one('bsTransitionEnd', function() {
					self.container.removeClass('overflow-hidden')
				}).emulateTransitionEnd(delay);
			}
		}
		
		
		this.hidden = function() {
			$window.off('.aside')
			//$modal.off('.aside')
			//			
			if( !ace.vars['transition'] || hasFade ) {
				$modal.addClass('aside-hidden');
				$modal.css('position', '');
			}
		}
		
		
		this.insideContainer = function() {
			var container = $('.main-container');

			var dialog = $modal.find('.modal-dialog');
			dialog.css({'right': '', 'left': ''});
			if( container.hasClass('container') ) {
				var flag = false;
				if(vertical == true) {
					dialog.css( placement, parseInt(($window.width() - container.width()) / 2) );
					flag = true;
				}

				//strange firefox issue, not redrawing properly on window resize (zoom in/out)!!!!
				//--- firefix is still having issue!
				if(flag && ace.vars['firefox']) {
					ace.helper.redraw(container[0]);
				}
			}
		}
		
		this.flip = function() {
			var flipSides = {right : 'left', left : 'right', top: 'bottom', bottom: 'top'};
			$modal.removeClass('aside-'+placement).addClass('aside-'+flipSides[placement]);
			placement = flipSides[placement];
		}

		var toggleButton = function() {
			var btn = $modal.find('.aside-trigger');
			if(btn.length == 0) return;
			btn.toggleClass('open');
			
			var icon = btn.find(ace.vars['.icon']);
			if(icon.length == 0) return;
			icon.toggleClass(icon.attr('data-icon1') + " " + icon.attr('data-icon2'));
		}
		

		this.initiate();
		
		if(this.container) this.container = $(this.container);
		$modal.appendTo(this.container || 'body'); 
	}


	$(document)
	.on('show.bs.modal', '.modal.aside', function(e) {
		$('.aside.in').modal('hide');//??? hide previous open ones?
		$(this).ace_aside('show');
	})
	.on('hide.bs.modal', '.modal.aside', function(e) {
		$(this).ace_aside('hide');
	})
	.on('shown.bs.modal', '.modal.aside', function(e) {
		$(this).ace_aside('shown');
	})
	.on('hidden.bs.modal', '.modal.aside', function(e) {
		$(this).ace_aside('hidden');
	})
	
	

	
	$(window).on('resize.aside_container', function() {
		$('.modal.aside').ace_aside('insideContainer');
	});
	$(document).on('settings.ace.aside', function(e, event_name) {
		if(event_name == 'main_container_fixed') $('.modal.aside').ace_aside('insideContainer');
	});

	$.fn.aceAside = $.fn.ace_aside = function (option, value) {
		var method_call;

		var $set = this.each(function () {
			var $this = $(this);
			var data = $this.data('ace_aside');
			var options = typeof option === 'object' && option;

			if (!data) $this.data('ace_aside', (data = new Aside(this, options)));
			if (typeof option === 'string' && typeof data[option] === 'function') {
				if(value instanceof Array) method_call = data[option].apply(data, value);
				else method_call = data[option](value);
			}
		});

		return (method_call === undefined) ? $set : method_call;
	}
	
	$.fn.ace_aside.defaults = {
		fixed: false,
		background: false,
		offset: false,
		body_scroll: false,
		transition: true,
		scroll_style: 'scroll-dark no-track',
		container: null,
		backdrop: false,
		placement: 'right'
     }

})(window.jQuery);/**
 Required. Ace's Basic File to Initiliaze Different Parts and Some Variables.
*/


//some basic variables
(function(undefined) {
	if( !('ace' in window) ) window['ace'] = {}
	if( !('helper' in window['ace']) ) window['ace'].helper = {}
	if( !('vars' in window['ace']) ) window['ace'].vars = {}
	window['ace'].vars['icon'] = ' ace-icon ';
	window['ace'].vars['.icon'] = '.ace-icon';

	ace.vars['touch']	= ('ontouchstart' in window);//(('ontouchstart' in document.documentElement) || (window.DocumentTouch && document instanceof DocumentTouch));
	
	//sometimes the only good way to work around browser's pecularities is to detect them using user-agents
	//though it's not accurate
	var agent = navigator.userAgent
	ace.vars['webkit'] = !!agent.match(/AppleWebKit/i)
	ace.vars['safari'] = !!agent.match(/Safari/i) && !agent.match(/Chrome/i);
	ace.vars['android'] = ace.vars['safari'] && !!agent.match(/Android/i)
	ace.vars['ios_safari'] = !!agent.match(/OS ([4-9])(_\d)+ like Mac OS X/i) && !agent.match(/CriOS/i)
	
	ace.vars['ie'] = window.navigator.msPointerEnabled || (document.all && document.querySelector);//8-11
	ace.vars['old_ie'] = document.all && !document.addEventListener;//8 and below
	ace.vars['very_old_ie']	= document.all && !document.querySelector;//7 and below
	ace.vars['firefox'] = 'MozAppearance' in document.documentElement.style;
	
	ace.vars['non_auto_fixed'] = ace.vars['android'] || ace.vars['ios_safari'];
})();



(function($ , undefined) {
	//sometimes we try to use 'tap' event instead of 'click' if jquery mobile plugin is available
	ace['click_event'] = ace.vars['touch'] && $.fn.tap ? 'tap' : 'click';
})(jQuery);




//document ready function
jQuery(function($) {
	basics();
	enableSidebar();
	
	enableDemoAjax();
	handleScrollbars();
	
	dropdownAutoPos();
	
	navbarHelpers();
	sidebarTooltips();
	
	scrollTopBtn();
	
	someBrowserFix();
	
	bsCollapseToggle();
	smallDeviceDropdowns();
	
	////////////////////////////

	function basics() {
		// for android and ios we don't use "top:auto" when breadcrumbs is fixed
		if(ace.vars['non_auto_fixed']) {
			$('body').addClass('mob-safari');
		}

		ace.vars['transition'] = !!$.support.transition.end
	}
	
	function enableSidebar() {
		//initiate sidebar function
		var $sidebar = $('.sidebar');
		if($.fn.ace_sidebar) $sidebar.ace_sidebar();
		if($.fn.ace_sidebar_scroll) $sidebar.ace_sidebar_scroll({
			//for other options please see documentation
			'include_toggle': false || ace.vars['safari'] || ace.vars['ios_safari'] //true = include toggle button in the scrollbars
		});
		if($.fn.ace_sidebar_hover)	$sidebar.ace_sidebar_hover({
			'sub_hover_delay': 750,
			'sub_scroll_style': 'no-track scroll-thin scroll-margin scroll-visible'
		});
	}

	
	//Load content via ajax
	function enableDemoAjax() {		
		if(!$.fn.ace_ajax) return;
 
		if(window.Pace) {
			window.paceOptions = {
				ajax: true,
				document: true,
				eventLag: false // disabled
				//elements: {selectors: ['.page-content-area']}
			}
		}

		var demo_ajax_options = {
			 'close_active': true,
			 
			 'default_url': 'page/index',//default hash
			 'content_url': function(hash) {
				//***NOTE***
				//this is for Ace demo only, you should change it to return a valid URL
				//please refer to documentation for more info

				if( !hash.match(/^page\//) ) return false;
				var path = document.location.pathname;

				//for example in Ace HTML demo version we convert /ajax/index.html#page/gallery to > /ajax/content/gallery.html and load it
				if(path.match(/(\/ajax\/)(index\.html)?/))
					return path.replace(/(\/ajax\/)(index\.html)?/, '/ajax/content/'+hash.replace(/^page\//, '')+'.html') ;

				//for example in Ace PHP demo version we convert "ajax.php#page/dashboard" to "ajax.php?page=dashboard" and load it
				return path + "?" + hash.replace(/\//, "=");
			  }			  
		}
		   
		//for IE9 and below we exclude PACE loader (using conditional IE comments)
		//for other browsers we use the following extra ajax loader options
		if(window.Pace) {
			demo_ajax_options['loading_overlay'] = 'body';//the opaque overlay is applied to 'body'
		}

		//initiate ajax loading on this element( which is .page-content-area[data-ajax-content=true] in Ace's demo)
		$('[data-ajax-content=true]').ace_ajax(demo_ajax_options)

		//if general error happens and ajax is working, let's stop loading icon & PACE
		$(window).on('error.ace_ajax', function() {
			$('[data-ajax-content=true]').each(function() {
				var $this = $(this);
				if( $this.ace_ajax('working') ) {
					if(window.Pace && Pace.running) Pace.stop();
					$this.ace_ajax('stopLoading', true);
				}
			})
		})
	}

	/////////////////////////////

	function handleScrollbars() {
		//add scrollbars for navbar dropdowns
		var has_scroll = !!$.fn.ace_scroll;
		if(has_scroll) $('.dropdown-content').ace_scroll({reset: false, mouseWheelLock: true})

		//reset scrolls bars on window resize
		if(has_scroll && !ace.vars['old_ie']) {//IE has an issue with widget fullscreen on ajax?!!!
			$(window).on('resize.reset_scroll', function() {
				$('.ace-scroll:not(.scroll-disabled)').not(':hidden').ace_scroll('reset');
			});
			if(has_scroll) $(document).on('settings.ace.reset_scroll', function(e, name) {
				if(name == 'sidebar_collapsed') $('.ace-scroll:not(.scroll-disabled)').not(':hidden').ace_scroll('reset');
			});
		}
	}


	function dropdownAutoPos() {
		//change a dropdown to "dropup" depending on its position
		$(document).on('click.dropdown.pos', '.dropdown-toggle[data-position="auto"]', function() {
			var offset = $(this).offset();
			var parent = $(this.parentNode);

			if ( parseInt(offset.top + $(this).height()) + 50 
					>
				(ace.helper.scrollTop() + ace.helper.winHeight() - parent.find('.dropdown-menu').eq(0).height()) 
				) parent.addClass('dropup');
			else parent.removeClass('dropup');
		});
	}

	
	function navbarHelpers() {
		//prevent dropdowns from hiding when a from is clicked
		/**$(document).on('click', '.dropdown-navbar form', function(e){
			e.stopPropagation();
		});*/


		//disable navbar icon animation upon click
		$('.ace-nav [class*="icon-animated-"]').closest('a').one('click', function(){
			var icon = $(this).find('[class*="icon-animated-"]').eq(0);
			var $match = icon.attr('class').match(/icon\-animated\-([\d\w]+)/);
			icon.removeClass($match[0]);
		});


		//prevent dropdowns from hiding when a tab is selected
		$(document).on('click', '.dropdown-navbar .nav-tabs', function(e){
			e.stopPropagation();
			var $this , href
			var that = e.target
			if( ($this = $(e.target).closest('[data-toggle=tab]')) && $this.length > 0) {
				$this.tab('show');
				e.preventDefault();
				$(window).triggerHandler('resize.navbar.dropdown')
			}
		});
	}

	
	function sidebarTooltips() {
		//tooltip in sidebar items
		$('.sidebar .nav-list .badge[title],.sidebar .nav-list .badge[title]').each(function() {
			var tooltip_class = $(this).attr('class').match(/tooltip\-(?:\w+)/);
			tooltip_class = tooltip_class ? tooltip_class[0] : 'tooltip-error';
			$(this).tooltip({
				'placement': function (context, source) {
					var offset = $(source).offset();

					if( parseInt(offset.left) < parseInt(document.body.scrollWidth / 2) ) return 'right';
					return 'left';
				},
				container: 'body',
				template: '<div class="tooltip '+tooltip_class+'"><div class="tooltip-arrow"></div><div class="tooltip-inner"></div></div>'
			});
		});
		
		//or something like this if items are dynamically inserted
		/**
		$('.sidebar').tooltip({
			'placement': function (context, source) {
				var offset = $(source).offset();

				if( parseInt(offset.left) < parseInt(document.body.scrollWidth / 2) ) return 'right';
				return 'left';
			},
			selector: '.nav-list .badge[title],.nav-list .label[title]',
			container: 'body',
			template: '<div class="tooltip tooltip-error"><div class="tooltip-arrow"></div><div class="tooltip-inner"></div></div>'
		});
		*/
	}
	
	

	function scrollTopBtn() {
		//the scroll to top button
		var scroll_btn = $('.btn-scroll-up');
		if(scroll_btn.length > 0) {
			var is_visible = false;
			$(window).on('scroll.scroll_btn', function() {
				var scroll = ace.helper.scrollTop();
				var h = ace.helper.winHeight();
				var body_sH = document.body.scrollHeight;
				if(scroll > parseInt(h / 4) || (scroll > 0 && body_sH >= h && h + scroll >= body_sH - 1)) {//|| for smaller pages, when reached end of page
					if(!is_visible) {
						scroll_btn.addClass('display');
						is_visible = true;
					}
				} else {
					if(is_visible) {
						scroll_btn.removeClass('display');
						is_visible = false;
					}
				}
			}).triggerHandler('scroll.scroll_btn');

			scroll_btn.on(ace.click_event, function(){
				var duration = Math.min(500, Math.max(100, parseInt(ace.helper.scrollTop() / 3)));
				$('html,body').animate({scrollTop: 0}, duration);
				return false;
			});
		}
	}


	
	function someBrowserFix() {
		//chrome and webkit have a problem here when resizing from 479px to more
		//we should force them redraw the navbar!
		if( ace.vars['webkit'] ) {
			var ace_nav = $('.ace-nav').get(0);
			if( ace_nav ) $(window).on('resize.webkit_fix' , function(){
				ace.helper.redraw(ace_nav);
			});
		}
		
		
		//fix an issue with ios safari, when an element is fixed and an input receives focus
		if(ace.vars['ios_safari']) {
		  $(document).on('ace.settings.ios_fix', function(e, event_name, event_val) {
			if(event_name != 'navbar_fixed') return;

			$(document).off('focus.ios_fix blur.ios_fix', 'input,textarea,.wysiwyg-editor');
			if(event_val == true) {
			  $(document).on('focus.ios_fix', 'input,textarea,.wysiwyg-editor', function() {
				$(window).on('scroll.ios_fix', function() {
					var navbar = $('#navbar').get(0);
					if(navbar) ace.helper.redraw(navbar);
				});
			  }).on('blur.ios_fix', 'input,textarea,.wysiwyg-editor', function() {
				$(window).off('scroll.ios_fix');
			  })
			}
		  }).triggerHandler('ace.settings.ios_fix', ['navbar_fixed', $('#navbar').css('position') == 'fixed']);
		}
	}

	
	
	function bsCollapseToggle() {
		//bootstrap collapse component icon toggle
		$(document).on('hide.bs.collapse show.bs.collapse', function (ev) {
			var panel_id = ev.target.getAttribute('id')
			var panel = $('a[href*="#'+ panel_id+'"]');
			if(panel.length == 0) panel = $('a[data-target*="#'+ panel_id+'"]');
			if(panel.length == 0) return;

			panel.find(ace.vars['.icon']).each(function(){
				var $icon = $(this)

				var $match
				var $icon_down = null
				var $icon_up = null
				if( ($icon_down = $icon.attr('data-icon-show')) ) {
					$icon_up = $icon.attr('data-icon-hide')
				}
				else if( $match = $icon.attr('class').match(/fa\-(.*)\-(up|down)/) ) {
					$icon_down = 'fa-'+$match[1]+'-down'
					$icon_up = 'fa-'+$match[1]+'-up'
				}

				if($icon_down) {
					if(ev.type == 'show') $icon.removeClass($icon_down).addClass($icon_up)
						else $icon.removeClass($icon_up).addClass($icon_down)
						
					return false;//ignore other icons that match, one is enough
				}

			});
		})
	}
	

	
	//in small devices display navbar dropdowns like modal boxes
	function smallDeviceDropdowns() {
	  if(ace.vars['old_ie']) return;
	  
	  $('.ace-nav > li')
	  .on('shown.bs.dropdown.navbar', function(e) {
		adjustNavbarDropdown.call(this);
	  })
	  .on('hidden.bs.dropdown.navbar', function(e) {
		$(window).off('resize.navbar.dropdown');
		resetNavbarDropdown.call(this);
	  })
	 
	  function adjustNavbarDropdown() {
		var $sub = $(this).find('> .dropdown-menu');

		if( $sub.css('position') == 'fixed' ) {
			var win_width = parseInt($(window).width());
			var offset_w = win_width > 320 ? 60 : (win_width > 240 ? 40 : 30);
			var avail_width = parseInt(win_width) - offset_w;
			var avail_height = parseInt($(window).height()) - 30;
			
			var width = parseInt(Math.min(avail_width , 320));
			//we set 'width' here for text wrappings and spacings to take effect before calculating scrollHeight
			$sub.css('width', width);

			var tabbed = false;
			var extra_parts = 0;
			var dropdown_content = $sub.find('.tab-pane.active .dropdown-content.ace-scroll');
			if(dropdown_content.length == 0) dropdown_content = $sub.find('.dropdown-content.ace-scroll');
			else tabbed = true;

			var parent_menu = dropdown_content.closest('.dropdown-menu');
			var scrollHeight = $sub[0].scrollHeight;
			if(dropdown_content.length == 1) {
				//sometimes there's no scroll-content, for example in detached scrollbars
				var content =  dropdown_content.find('.scroll-content')[0];
				if(content) {
					scrollHeight = content.scrollHeight;
				}
			
				extra_parts += parent_menu.find('.dropdown-header').outerHeight();
				extra_parts += parent_menu.find('.dropdown-footer').outerHeight();
				
				var tab_content = parent_menu.closest('.tab-content');
				if( tab_content.length != 0 ) {
					extra_parts += tab_content.siblings('.nav-tabs').eq(0).height();
				}
			}
			

			
			var height = parseInt(Math.min(avail_height , 480, scrollHeight + extra_parts));
			var left = parseInt(Math.abs((avail_width + offset_w - width)/2));
			var top = parseInt(Math.abs((avail_height + 30 - height)/2));

			
			var zindex = parseInt($sub.css('z-index')) || 0;

			$sub.css({'height': height, 'left': left, 'right': 'auto', 'top': top - (!tabbed ? 1 : 3)});
			if(dropdown_content.length == 1) {
				if(!ace.vars['touch']) {
					dropdown_content.ace_scroll('update', {size: height - extra_parts}).ace_scroll('enable').ace_scroll('reset');
				}
				else {
					dropdown_content
					.ace_scroll('disable').css('max-height', height - extra_parts).addClass('overflow-scroll');
				}
			}
			$sub.css('height', height + (!tabbed ? 2 : 7));//for bottom border adjustment and tab content paddings
			
			
			if($sub.hasClass('user-menu')) {
				$sub.css('height', '');//because of user-info hiding/showing at different widths, which changes above 'scrollHeight', so we remove it!
				
				//user menu is re-positioned in small widths
				//but we need to re-position again in small heights as well (modal mode)
				var user_info = $(this).find('.user-info');
				if(user_info.length == 1 && user_info.css('position') == 'fixed') {
					user_info.css({'left': left, 'right': 'auto', 'top': top, 'width': width - 2, 'max-width': width - 2, 'z-index': zindex + 1});
				}
				else user_info.css({'left': '', 'right': '', 'top': '', 'width': '', 'max-width': '', 'z-index': ''});
			}
			
			//dropdown's z-index is limited by parent .navbar's z-index (which doesn't make sense because dropdowns are fixed!)
			//so for example when in 'content-slider' page, fixed modal toggle buttons go above are dropdowns
			//so we increase navbar's z-index to fix this!
			$(this).closest('.navbar.navbar-fixed-top').css('z-index', zindex);
		}
		else {
			if($sub.length != 0) resetNavbarDropdown.call(this, $sub);
		}
		
		var self = this;
		$(window)
		.off('resize.navbar.dropdown')
		.one('resize.navbar.dropdown', function() {
			$(self).triggerHandler('shown.bs.dropdown.navbar');
		})
	  }

	  //reset scrollbars and user menu
	  function resetNavbarDropdown($sub) {
		$sub = $sub || $(this).find('> .dropdown-menu');
	  
	    if($sub.length > 0) {
			$sub
			.css({'width': '', 'height': '', 'left': '', 'right': '', 'top': ''})
			.find('.dropdown-content').each(function() {
				if(ace.vars['touch']) {
					$(this).css('max-height', '').removeClass('overflow-scroll');
				}

				var size = parseInt($(this).attr('data-size') || 0) || $.fn.ace_scroll.defaults.size;
				$(this).ace_scroll('update', {size: size}).ace_scroll('enable').ace_scroll('reset');
			})
			
			if( $sub.hasClass('user-menu') ) {
				var user_info = 
				$(this).find('.user-info')
				.css({'left': '', 'right': '', 'top': '', 'width': '', 'max-width': '', 'z-index': ''});
			}
		}
		
		$(this).closest('.navbar').css('z-index', '');
	  }
	}

});//jQuery document ready





//some ace helper functions
(function($$ , undefined) {//$$ is ace.helper
 $$.unCamelCase = function(str) {
	return str.replace(/([a-z])([A-Z])/g, function(match, c1, c2){ return c1+'-'+c2.toLowerCase() })
 }
 $$.strToVal = function(str) {
	var res = str.match(/^(?:(true)|(false)|(null)|(\-?[\d]+(?:\.[\d]+)?)|(\[.*\]|\{.*\}))$/i);

	var val = str;
	if(res) {
		if(res[1]) val = true;
		else if(res[2]) val = false;
		else if(res[3]) val = null;	
		else if(res[4]) val = parseFloat(str);
		else if(res[5]) {
			try { val = JSON.parse(str) }
			catch (err) {}
		}
	}

	return val;
 }
 $$.getAttrSettings = function(el, attr_list, prefix) {
	var list_type = attr_list instanceof Array ? 1 : 2;
	//attr_list can be Array or Object(key/value)
	var prefix = prefix ? prefix.replace(/([^\-])$/ , '$1-') : '';
	prefix = 'data-' + prefix;

	var settings = {}
	for(var li in attr_list) if(attr_list.hasOwnProperty(li)) {
		var name = list_type == 1 ? attr_list[li] : li;
		var attr_val, attr_name = $$.unCamelCase(name.replace(/[^A-Za-z0-9]{1,}/g , '-')).toLowerCase()

		if( ! ((attr_val = el.getAttribute(prefix + attr_name))  ) ) continue;
		settings[name] = $$.strToVal(attr_val);
	}

	return settings;
 }

 $$.scrollTop = function() {
	return document.scrollTop || document.documentElement.scrollTop || document.body.scrollTop
 }
 $$.winHeight = function() {
	return window.innerHeight || document.documentElement.clientHeight;
 }
 $$.redraw = function(elem, force) {
	var saved_val = elem.style['display'];
	elem.style.display = 'none';
	elem.offsetHeight;
	if(force !== true) {
		elem.style.display = saved_val;
	}
	else {
		//force redraw for example in old IE
		setTimeout(function() {
			elem.style.display = saved_val;
		}, 10);
	}
 }
})(ace.helper);/**
 <b>Load content via Ajax </b>. For more information please refer to documentation #basics/ajax
*/

(function($ , undefined) {
	var ajax_loaded_scripts = {}

	function AceAjax(contentArea, settings) {
		var $contentArea = $(contentArea);
		var self = this;
		$contentArea.attr('data-ajax-content', 'true');
		
		//get a list of 'data-*' attributes that override 'defaults' and 'settings'
		var attrib_values = ace.helper.getAttrSettings(contentArea, $.fn.ace_ajax.defaults);
		this.settings = $.extend({}, $.fn.ace_ajax.defaults, settings, attrib_values);


		var working = false;
		var $overlay = $();//empty set

		this.force_reload = false;//set jQuery ajax's cache option to 'false' to reload content
		this.loadUrl = function(hash, cache) {
			var url = false;
			hash = hash.replace(/^(\#\!)?\#/, '');
			
			this.force_reload = (cache === false)
			
			if(typeof this.settings.content_url === 'function') url = this.settings.content_url(hash);
			if(typeof url === 'string') this.getUrl(url, hash, false);
		}
		
		this.loadAddr = function(url, hash, cache) {
			this.force_reload = (cache === false);
			this.getUrl(url, hash, false);
		}
		
		this.getUrl = function(url, hash, manual_trigger) {
			if(working) {
				return;
			}
		
			var event
			$contentArea.trigger(event = $.Event('ajaxloadstart'), {url: url, hash: hash})
			if (event.isDefaultPrevented()) return;
			
			self.startLoading();

			$.ajax({
				'url': url,
				'cache': !this.force_reload
			})
			.error(function() {
				$contentArea.trigger('ajaxloaderror', {url: url, hash: hash});
				
				self.stopLoading(true);
			})
			.done(function(result) {
				$contentArea.trigger('ajaxloaddone', {url: url, hash: hash});
				
				var link_element = null, link_text = '';;
				if(typeof self.settings.update_active === 'function') {
					link_element = self.settings.update_active.call(null, hash, url);
				}
				else if(self.settings.update_active === true && hash) {
					link_element = $('a[data-url="'+hash+'"]');
					if(link_element.length > 0) {
						var nav = link_element.closest('.nav');
						if(nav.length > 0) {
							nav.find('.active').each(function(){
								var $class = 'active';
								if( $(this).hasClass('hover') || self.settings.close_active ) $class += ' open';
								
								$(this).removeClass($class);							
								if(self.settings.close_active) {
									$(this).find(' > .submenu').css('display', '');
								}
							})
							
							var active_li = link_element.closest('li').addClass('active').parents('.nav li').addClass('active open');
							nav.closest('.sidebar[data-sidebar-scroll=true]').each(function() {
								var $this = $(this);
								$this.ace_sidebar_scroll('reset');
								if(manual_trigger) $this.ace_sidebar_scroll('scroll_to_active');//first time only
							})
						}
					}
				}

				/////////
				if(typeof self.settings.update_breadcrumbs === 'function') {
					link_text = self.settings.update_breadcrumbs.call(null, hash, url, link_element);
				}
				else if(self.settings.update_breadcrumbs === true && link_element != null && link_element.length > 0) {
					link_text = updateBreadcrumbs(link_element);
				}
				/////////

				//convert "title" and "link" tags to "div" tags for later processing
				result = String(result)
					.replace(/<(title|link)([\s\>])/gi,'<div class="hidden ajax-append-$1"$2')
					.replace(/<\/(title|link)\>/gi,'</div>')
			
				
				$overlay.addClass('content-loaded').detach();
				$contentArea.empty().html(result);
				
				$(self.settings.loading_overlay || $contentArea).append($overlay);
	
				//remove previous stylesheets inserted via ajax
				setTimeout(function() {
					$('head').find('link.ace-ajax-stylesheet').remove();

					var main_selectors = ['link.ace-main-stylesheet', 'link#main-ace-style', 'link[href*="/ace.min.css"]', 'link[href*="/ace.css"]']
					var ace_style = [];
					for(var m = 0; m < main_selectors.length; m++) {
						ace_style = $('head').find(main_selectors[m]).first();
						if(ace_style.length > 0) break;
					}
					
					$contentArea.find('.ajax-append-link').each(function(e) {
						var $link = $(this);
						if ( $link.attr('href') ) {
							var new_link = jQuery('<link />', {type : 'text/css', rel: 'stylesheet', 'class': 'ace-ajax-stylesheet'})
							if( ace_style.length > 0 ) new_link.insertBefore(ace_style);
							else new_link.appendTo('head');
							new_link.attr('href', $link.attr('href'));//we set "href" after insertion, for IE to work
						}
						$link.remove();
					})
				}, 10);

				//////////////////////

				if(typeof self.settings.update_title === 'function') {
					self.settings.update_title.call(null, hash, url, link_text);
				}
				else if(self.settings.update_title === true) {
					updateTitle(link_text);
				}
				

				if( !manual_trigger ) {
					$('html,body').animate({scrollTop: 0}, 250);
				}

				//////////////////////
				$contentArea.trigger('ajaxloadcomplete', {url: url, hash: hash});
				//////////////////////
				
				self.stopLoading();
			})
		}
		
		
		///////////////////////
		var fixPos = false;
		var loadTimer = null;
		this.startLoading = function() {
			if(working) return;
			working = true;
			
			if(!this.settings.loading_overlay && $contentArea.css('position') == 'static') {
				$contentArea.css('position', 'relative');//for correct icon positioning
				fixPos = true;
			}
				
			$overlay.remove();
			$overlay = $('<div class="ajax-loading-overlay"><i class="ajax-loading-icon '+(this.settings.loading_icon || '')+'"></i> '+this.settings.loading_text+'</div>')

			if(this.settings.loading_overlay == 'body') $('body').append($overlay.addClass('ajax-overlay-body'));
			else if(this.settings.loading_overlay) $(this.settings.loading_overlay).append($overlay);
			else $contentArea.append($overlay);

			
			if(this.settings.max_load_wait !== false) 
			 loadTimer = setTimeout(function() {
				loadTimer = null;
				if(!working) return;
				
				var event
				$contentArea.trigger(event = $.Event('ajaxloadlong'))
				if (event.isDefaultPrevented()) return;
				
				self.stopLoading(true);
			 }, this.settings.max_load_wait * 1000);
		}
		
		this.stopLoading = function(stopNow) {
			if(stopNow === true) {
				working = false;
				
				$overlay.remove();
				if(fixPos) {
					$contentArea.css('position', '');//restore previous 'position' value
					fixPos = false;
				}
				
				if(loadTimer != null) {
					clearTimeout(loadTimer);
					loadTimer = null;
				}
			}
			else {
				$overlay.addClass('almost-loaded');
				
				$contentArea.one('ajaxscriptsloaded.inner_call', function() {
					self.stopLoading(true);
					/**
					if(window.Pace && Pace.running == true) {
						Pace.off('done');
						Pace.once('done', function() { self.stopLoading(true) })
					}
					else self.stopLoading(true);
					*/
				})
			}
		}
		
		this.working = function() {
			return working;
		}
		///////////////////////
		
		
		
		function updateBreadcrumbs(link_element) {
			var link_text = '';
		 
			//update breadcrumbs
			var breadcrumbs = $('.breadcrumb');
			if(breadcrumbs.length > 0 && breadcrumbs.is(':visible')) {
				breadcrumbs.find('> li:not(:first-child)').remove();

				var i = 0;		
				link_element.parents('.nav li').each(function() {
					var link = $(this).find('> a');
					
					var link_clone = link.clone();
					link_clone.find('i,.fa,.glyphicon,.ace-icon,.menu-icon,.badge,.label').remove();
					var text = link_clone.text();
					link_clone.remove();
					
					var href = link.attr('href');

					if(i == 0) {
						var li = $('<li class="active"></li>').appendTo(breadcrumbs);
						li.text(text);
						link_text = text;
					}
					else {
						var li = $('<li><a /></li>').insertAfter(breadcrumbs.find('> li:first-child'));
						li.find('a').attr('href', href).text(text);
					}
					i++;
				})
			}
			
			return link_text;
		 }
		 
		 function updateTitle(link_text) {
			var $title = $contentArea.find('.ajax-append-title');
			if($title.length > 0) {
				document.title = $title.text();
				$title.remove();
			}
			else if(link_text.length > 0) {
				var extra = $.trim(String(document.title).replace(/^(.*)[\-]/, ''));//for example like " - Ace Admin"
				if(extra) extra = ' - ' + extra;
				link_text = $.trim(link_text) + extra;
			}
		 }
		 
		 
		 this.loadScripts = function(scripts, callback) {
			$.ajaxPrefilter('script', function(opts) {opts.cache = true});
			setTimeout(function() {
				//let's keep a list of loaded scripts so that we don't load them more than once!
				
				function finishLoading() {
					if(typeof callback === 'function') callback();
					$('.btn-group[data-toggle="buttons"] > .btn').button();
					
					$contentArea.trigger('ajaxscriptsloaded');
				}
				
				//var deferreds = [];
				var deferred_count = 0;//deferreds count
				var resolved = 0;
				for(var i = 0; i < scripts.length; i++) if(scripts[i]) {
					(function() {
						var script_name = "js-"+scripts[i].replace(/[^\w\d\-]/g, '-').replace(/\-\-/g, '-');
						if( ajax_loaded_scripts[script_name] !== true )	deferred_count++;
					})()
				}
				

				function nextScript(index) {
					index += 1;
					if(index < scripts.length) loadScript(index);
					else {
						finishLoading();
					}
				}
				
				function loadScript(index) {
					index = index || 0;
					if(!scripts[index]) {//could be null sometimes
						return nextScript(index);
					}
				
					var script_name = "js-"+scripts[index].replace(/[^\w\d\-]/g, '-').replace(/\-\-/g, '-');
					//only load scripts that are not loaded yet!
					if( ajax_loaded_scripts[script_name] !== true ) {
						$.getScript(scripts[index])
						.done(function() {
							ajax_loaded_scripts[script_name] = true;
						})
						//.fail(function() {
						//})
						.complete(function() {
							resolved++;
							if(resolved >= deferred_count && working) {
								finishLoading();
							}
							else {
								nextScript(index);
							}
						})
					}
					else {//script previoisly loaded
						nextScript(index);
					}
				}
				
				
				if (deferred_count > 0) {
					loadScript();
				}
				else {
					finishLoading();
				}

			}, 10)
		}
		
		
		
		/////////////////
		$(window)
		.off('hashchange.ace_ajax')
		.on('hashchange.ace_ajax', function(e, manual_trigger) {
			var hash = $.trim(window.location.hash);
			if(!hash || hash.length == 0) return;
			
			self.loadUrl(hash);
		}).trigger('hashchange.ace_ajax', [true]);
		
		var hash = $.trim(window.location.hash);
		if(!hash && this.settings.default_url) window.location.hash = this.settings.default_url;

	}//AceAjax



	$.fn.aceAjax = $.fn.ace_ajax = function (option, value, value2, value3) {
		var method_call;

		var $set = this.each(function () {
			var $this = $(this);
			var data = $this.data('ace_ajax');
			var options = typeof option === 'object' && option;

			if (!data) $this.data('ace_ajax', (data = new AceAjax(this, options)));
			if (typeof option === 'string' && typeof data[option] === 'function') {
				if(value3 != undefined) method_call = data[option](value, value2, value3);
				else if(value2 != undefined) method_call = data[option](value, value2);
				else method_call = data[option](value);
			}
		});

		return (method_call === undefined) ? $set : method_call;
	}
	
	
	
	$.fn.aceAjax.defaults = $.fn.ace_ajax.defaults = {
		content_url: false,
		default_url: false,
		loading_icon: 'fa fa-spin fa-spinner fa-2x orange',
		loading_text: '',
		loading_overlay: null,
		update_breadcrumbs: true,
		update_title: true,
		update_active: true,
		close_active: false,
		max_load_wait: false
     }

})(window.jQuery);

/**
 <b>Custom drag event for touch devices</b> used in scrollbars.
 For better touch event handling and extra options a more advanced solution such as <u>Hammer.js</u> is recommended.
*/

//based on but not dependent on jQuery mobile
/*
* jQuery Mobile v1.3.2
* http://jquerymobile.com
*
* Copyright 2010, 2013 jQuery Foundation, Inc. and other contributors
* Released under the MIT license.
* http://jquery.org/license
*
*/
(function($ , undefined) {

	if(!ace.vars['touch']) return;

	var touchStartEvent = "touchstart MSPointerDown pointerdown",// : "mousedown",
			touchStopEvent  =  "touchend touchcancel MSPointerUp MSPointerCancel pointerup pointercancel",// : "mouseup",
			touchMoveEvent  =  "touchmove MSPointerMove MSPointerHover pointermove";// : "mousemove";


	$.event.special.ace_drag = {
		setup: function() {
			var min_threshold = 0;
		
			var $this = $(this);
			$this.on(touchStartEvent, function(event) {		
				var data = event.originalEvent.touches ?
					event.originalEvent.touches[ 0 ] :
					event,
					start = {
						//time: Date.now(),
						coords: [ data.pageX, data.pageY ],
						origin: $(event.target)
					},
					stop;
					//start.origin.trigger({'type' : 'ace_dragStart', 'start':(start || [-1,-1])});
					
					var direction = false, dx = 0, dy = 0;

				function moveHandler(event) {
					if (!start) {
						return;
					}
					var data = event.originalEvent.touches ?
							event.originalEvent.touches[ 0 ] :
							event;
					stop = {
						coords: [ data.pageX, data.pageY ]
					};
					
					// prevent scrolling
					//if ( Math.abs(start.coords[1] - stop.coords[1]) > 0 || Math.abs(start.coords[0] - stop.coords[01]) > 0 ) {
						//event.preventDefault();
					//}


					if (start && stop) {
						dx = 0;
						dy = 0;

						direction = 
							(
							 Math.abs(dy = start.coords[ 1 ] - stop.coords[ 1 ]) > min_threshold
								&& 
							 Math.abs(dx = start.coords[ 0 ] - stop.coords[ 0 ]) <= Math.abs(dy)
							)
							? 
							(dy > 0 ? 'up' : 'down')
							:
							(
							 Math.abs(dx = start.coords[ 0 ] - stop.coords[ 0 ]) > min_threshold
								&& 
							 Math.abs( dy ) <= Math.abs(dx)
							)
							?
							(dx > 0 ? 'left' : 'right')
							:
							false;
							

							if( direction !== false ) {
							 var retval = {cancel: false}
							 start.origin.trigger({
								'type': 'ace_drag',
								//'start': start.coords,
								//'stop': stop.coords,
								'direction': direction,
								'dx': dx,
								'dy': dy,
								'retval': retval
							 })

		 					  // prevent document scrolling unless retval.cancel == true
							  if( retval.cancel == false ) event.preventDefault();
							}
					}
					start.coords[0] = stop.coords[0];
					start.coords[1] = stop.coords[1];
				}

				$this
				.on(touchMoveEvent, moveHandler)
				.one(touchStopEvent, function(event) {
					$this.off(touchMoveEvent, moveHandler);
					//start.origin.trigger({'type' : 'ace_dragEnd', 'stop':(stop || [-1,-1])});
					
					start = stop = undefined;
				
				});
			});
		}
	}

})(window.jQuery);/**
 <b>Sidebar functions</b>. Collapsing/expanding, toggling mobile view menu and other sidebar functions.
*/

(function($ , undefined) {
	var sidebar_count = 0;

	function Sidebar(sidebar, settings) {
		var self = this;
		this.$sidebar = $(sidebar);
		this.$sidebar.attr('data-sidebar', 'true');
		if( !this.$sidebar.attr('id') ) this.$sidebar.attr( 'id' , 'id-sidebar-'+(++sidebar_count) )

		
		//get a list of 'data-*' attributes that override 'defaults' and 'settings'
		var attrib_values = ace.helper.getAttrSettings(sidebar, $.fn.ace_sidebar.defaults, 'sidebar-');
		this.settings = $.extend({}, $.fn.ace_sidebar.defaults, settings, attrib_values);


		//some vars
		this.minimized = false;//will be initiated later
		this.collapsible = false;//...
		this.horizontal = false;//...
		this.mobile_view = false;//


		this.vars = function() {
			return {'minimized': this.minimized, 'collapsible': this.collapsible, 'horizontal': this.horizontal, 'mobile_view': this.mobile_view}
		}
		this.get = function(name) {
			if(this.hasOwnProperty(name)) return this[name];
		}
		this.set = function(name, value) {
			if(this.hasOwnProperty(name)) this[name] = value;
		}
		

		this.ref = function() {
			//return a reference to self
			return this;
		}

		var toggleIcon = function(minimized) {
			var icon = $(this).find(ace.vars['.icon']), icon1, icon2;
			if(icon.length > 0) {
				icon1 = icon.attr('data-icon1');//the icon for expanded state
				icon2 = icon.attr('data-icon2');//the icon for collapsed state

				if(minimized !== undefined) {
					if(minimized) icon.removeClass(icon1).addClass(icon2);
					else icon.removeClass(icon2).addClass(icon1);
				}
				else {
					icon.toggleClass(icon1).toggleClass(icon2);
				}
			}
		}		
		
		var findToggleBtn = function() {
			var toggle_btn = self.$sidebar.find('.sidebar-collapse');
			if(toggle_btn.length == 0) toggle_btn = $('.sidebar-collapse[data-target="#'+(self.$sidebar.attr('id')||'')+'"]');
			if(toggle_btn.length != 0) toggle_btn = toggle_btn[0];
			else toggle_btn = null;
			
			return toggle_btn;
		}
		
		//collapse/expand button
		this.toggleMenu = function(toggle_btn, save) {
			if(this.collapsible) return;

			//var minimized = this.$sidebar.hasClass('menu-min');
			this.minimized = !this.minimized;
			
			try {
				//toggle_btn can also be a param to indicate saving to cookie or not?! if toggle_btn === false, it won't be saved
				ace.settings.sidebar_collapsed(sidebar, this.minimized, !(toggle_btn === false || save === false));//@ ace-extra.js
			} catch(e) {
				if(this.minimized)
					this.$sidebar.addClass('menu-min');
				else this.$sidebar.removeClass('menu-min');
			}
	
			if( !toggle_btn ) {
				toggle_btn = findToggleBtn();
			}
			if(toggle_btn) {
				toggleIcon.call(toggle_btn, this.minimized);
			}

			//force redraw for ie8
			if(ace.vars['old_ie']) ace.helper.redraw(sidebar);
		}
		this.collapse = function(toggle_btn, save) {
			if(this.collapsible) return;
			this.minimized = false;
			
			this.toggleMenu(toggle_btn, save);
		}
		this.expand = function(toggle_btn, save) {
			if(this.collapsible) return;
			this.minimized = true;
			
			this.toggleMenu(toggle_btn, save);
		}
		

		
		//collapse/expand in 2nd mobile style
		this.toggleResponsive = function(toggle_btn) {
			if(!this.mobile_view || this.mobile_style != 3) return;
		
			if( this.$sidebar.hasClass('menu-min') ) {
				//remove menu-min because it interferes with responsive-max
				this.$sidebar.removeClass('menu-min');
				var btn = findToggleBtn();
				if(btn) toggleIcon.call(btn);
			}


			this.minimized = !this.$sidebar.hasClass('responsive-min');
			this.$sidebar.toggleClass('responsive-min responsive-max');


			if( !toggle_btn ) {
				toggle_btn = this.$sidebar.find('.sidebar-expand');
				if(toggle_btn.length == 0) toggle_btn = $('.sidebar-expand[data-target="#'+(this.$sidebar.attr('id')||'')+'"]');
				if(toggle_btn.length != 0) toggle_btn = toggle_btn[0];
				else toggle_btn = null;
			}
			
			if(toggle_btn) {
				var icon = $(toggle_btn).find(ace.vars['.icon']), icon1, icon2;
				if(icon.length > 0) {
					icon1 = icon.attr('data-icon1');//the icon for expanded state
					icon2 = icon.attr('data-icon2');//the icon for collapsed state

					icon.toggleClass(icon1).toggleClass(icon2);
				}
			}

			$(document).triggerHandler('settings.ace', ['sidebar_collapsed' , this.minimized]);
		}
		
		//some helper functions
		this.is_collapsible = function() {
			var toggle
			return (this.$sidebar.hasClass('navbar-collapse'))
			&& ((toggle = $('.navbar-toggle[data-target="#'+(this.$sidebar.attr('id')||'')+'"]').get(0)) != null)
			&&  toggle.scrollHeight > 0
			//sidebar is collapsible and collapse button is visible?
		}
		this.is_mobile_view = function() {
			var toggle
			return ((toggle = $('.menu-toggler[data-target="#'+(this.$sidebar.attr('id')||'')+'"]').get(0)) != null)
			&&  toggle.scrollHeight > 0
		}


		//toggling submenu
		this.$sidebar.on(ace.click_event+'.ace.submenu', '.nav-list', function (ev) {
			var nav_list = this;

			//check to see if we have clicked on an element which is inside a .dropdown-toggle element?!
			//if so, it means we should toggle a submenu
			var link_element = $(ev.target).closest('a');
			if(!link_element || link_element.length == 0) return;//return if not clicked inside a link element

			var minimized  = self.minimized && !self.collapsible;
			//if .sidebar is .navbar-collapse and in small device mode, then let minimized be uneffective
	
			if( !link_element.hasClass('dropdown-toggle') ) {//it doesn't have a submenu return
				//just one thing before we return
				//if sidebar is collapsed(minimized) and we click on a first level menu item
				//and the click is on the icon, not on the menu text then let's cancel event and cancel navigation
				//Good for touch devices, that when the icon is tapped to see the menu text, navigation is cancelled
				//navigation is only done when menu text is tapped

				if( ace.click_event == 'tap'
					&&
					minimized
					&&
					link_element.get(0).parentNode.parentNode == nav_list )//only level-1 links
				{
					var text = link_element.find('.menu-text').get(0);
					if( text != null && ev.target != text && !$.contains(text , ev.target) ) {//not clicking on the text or its children
						ev.preventDefault();
						return false;
					}
				}


				//ios safari only has a bit of a problem not navigating to link address when scrolling down
				//specify data-link attribute to ignore this
				if(ace.vars['ios_safari'] && link_element.attr('data-link') !== 'false') {
					//only ios safari has a bit of a problem not navigating to link address when scrolling down
					//please see issues section in documentation
					document.location = link_element.attr('href');
					ev.preventDefault();
					return false;
				}

				return;
			}
			
			ev.preventDefault();
			
			


			var sub = link_element.siblings('.submenu').get(0);
			if(!sub) return false;
			var $sub = $(sub);

			var height_change = 0;//the amount of height change in .nav-list

			var parent_ul = sub.parentNode.parentNode;
			if
			(
				( minimized && parent_ul == nav_list )
				 || 
				( ( $sub.parent().hasClass('hover') && $sub.css('position') == 'absolute' ) && !self.collapsible )
			)
			{
				return false;
			}

			
			var sub_hidden = (sub.scrollHeight == 0)

			//if not open and visible, let's open it and make it visible
			if( sub_hidden ) {//being shown now
			  $(parent_ul).find('> .open > .submenu').each(function() {
				//close all other open submenus except for the active one
				if(this != sub && !$(this.parentNode).hasClass('active')) {
					height_change -= this.scrollHeight;
					self.hide(this, self.settings.duration, false);
				}
			  })
			}

			if( sub_hidden ) {//being shown now
				self.show(sub, self.settings.duration);
				//if a submenu is being shown and another one previously started to hide, then we may need to update/hide scrollbars
				//but if no previous submenu is being hidden, then no need to check if we need to hide the scrollbars in advance
				if(height_change != 0) height_change += sub.scrollHeight;//we need new updated 'scrollHeight' here
			} else {
				self.hide(sub, self.settings.duration);
				height_change -= sub.scrollHeight;
				//== -1 means submenu is being hidden
			}

			//hide scrollbars if content is going to be small enough that scrollbars is not needed anymore
			//do this almost before submenu hiding begins
			//but when minimized submenu's toggle should have no effect
			if (height_change != 0) {
				if(self.$sidebar.attr('data-sidebar-scroll') == 'true' && !self.minimized) 
					self.$sidebar.ace_sidebar_scroll('prehide', height_change)
			}

			return false;
		})

		var submenu_working = false;
		this.show = function(sub, $duration, shouldWait) {
			//'shouldWait' indicates whether to wait for previous transition (submenu toggle) to be complete or not?
			shouldWait = (shouldWait !== false);
			if(shouldWait && submenu_working) return false;
					
			var $sub = $(sub);
			var event;
			$sub.trigger(event = $.Event('show.ace.submenu'))
			if (event.isDefaultPrevented()) {
				return false;
			}
			
			if(shouldWait) submenu_working = true;


			$duration = $duration || this.settings.duration;
			
			$sub.css({
				height: 0,
				overflow: 'hidden',
				display: 'block'
			})
			.removeClass('nav-hide').addClass('nav-show')//only for window < @grid-float-breakpoint and .navbar-collapse.menu-min
			.parent().addClass('open');
			
			sub.scrollTop = 0;//this is for submenu_hover when sidebar is minimized and a submenu is scrollTop'ed using scrollbars ...

			if( $duration > 0 ) {
			  $sub.css({height: sub.scrollHeight,
				'transition-property': 'height',
				'transition-duration': ($duration/1000)+'s'})
			}

			var complete = function(ev, trigger) {
				ev && ev.stopPropagation();
				$sub
				.css({'transition-property': '', 'transition-duration': '', overflow:'', height: ''})
				//if(ace.vars['webkit']) ace.helper.redraw(sub);//little Chrome issue, force redraw ;)

				if(trigger !== false) $sub.trigger($.Event('shown.ace.submenu'))
				
				if(shouldWait) submenu_working = false;
			}
			
			if( $duration > 0 && !!$.support.transition.end ) {
			  $sub.one($.support.transition.end, complete);
			}
			else complete();
			
			//there is sometimes a glitch, so maybe retry
			if(ace.vars['android']) {
				setTimeout(function() {
					complete(null, false);
					ace.helper.redraw(sub);
				}, $duration + 20);
			}

			return true;
		 }
		 
		 
		 this.hide = function(sub, $duration, shouldWait) {
			//'shouldWait' indicates whether to wait for previous transition (submenu toggle) to be complete or not?
			shouldWait = (shouldWait !== false);
			if(shouldWait && submenu_working) return false;
		 
			
			var $sub = $(sub);
			var event;
			$sub.trigger(event = $.Event('hide.ace.submenu'))
			if (event.isDefaultPrevented()) {
				return false;
			}
			
			if(shouldWait) submenu_working = true;
			

			$duration = $duration || this.settings.duration;
			
			$sub.css({
				height: sub.scrollHeight,
				overflow: 'hidden',
				display: 'block'
			})
			.parent().removeClass('open');

			sub.offsetHeight;
			//forces the "sub" to re-consider the new 'height' before transition

			if( $duration > 0 ) {
			  $sub.css({'height': 0,
				'transition-property': 'height',
				'transition-duration': ($duration/1000)+'s'});
			}


			var complete = function(ev, trigger) {
				ev && ev.stopPropagation();
				$sub
				.css({display: 'none', overflow:'', height: '', 'transition-property': '', 'transition-duration': ''})
				.removeClass('nav-show').addClass('nav-hide')//only for window < @grid-float-breakpoint and .navbar-collapse.menu-min

				if(trigger !== false) $sub.trigger($.Event('hidden.ace.submenu'))
				
				if(shouldWait) submenu_working = false;
			}

			if( $duration > 0 && !!$.support.transition.end ) {
			   $sub.one($.support.transition.end, complete);
			}
			else complete();


			//there is sometimes a glitch, so maybe retry
			if(ace.vars['android']) {
				setTimeout(function() {
					complete(null, false);
					ace.helper.redraw(sub);
				}, $duration + 20);
			}

			return true;
		 }

		 this.toggle = function(sub, $duration) {
			$duration = $duration || self.settings.duration;
		 
			if( sub.scrollHeight == 0 ) {//if an element is hidden scrollHeight becomes 0
				if( this.show(sub, $duration) ) return 1;
			} else {
				if( this.hide(sub, $duration) ) return -1;
			}
			return 0;
		 }


		//sidebar vars
		var minimized_menu_class  = 'menu-min';
		var responsive_min_class  = 'responsive-min';
		var horizontal_menu_class = 'h-sidebar';

		var sidebar_mobile_style = function() {
			//differnet mobile menu styles
			this.mobile_style = 1;//default responsive mode with toggle button inside navbar
			if(this.$sidebar.hasClass('responsive') && !$('.menu-toggler[data-target="#'+this.$sidebar.attr('id')+'"]').hasClass('navbar-toggle')) this.mobile_style = 2;//toggle button behind sidebar
			 else if(this.$sidebar.hasClass(responsive_min_class)) this.mobile_style = 3;//minimized menu
			  else if(this.$sidebar.hasClass('navbar-collapse')) this.mobile_style = 4;//collapsible (bootstrap style)
		}
		sidebar_mobile_style.call(self);
		  
		function update_vars() {
			this.mobile_view = this.mobile_style < 4 && this.is_mobile_view();
			this.collapsible = !this.mobile_view && this.is_collapsible();

			this.minimized = 
			(!this.collapsible && this.$sidebar.hasClass(minimized_menu_class))
			 ||
			(this.mobile_style == 3 && this.mobile_view && this.$sidebar.hasClass(responsive_min_class))

			this.horizontal = !(this.mobile_view || this.collapsible) && this.$sidebar.hasClass(horizontal_menu_class)
		}

		//update some basic variables
		$(window).on('resize.sidebar.vars' , function(){
			update_vars.call(self);
		}).triggerHandler('resize.sidebar.vars')

	}//end of Sidebar
	

	//sidebar events
	
	//menu-toggler
	$(document)
	.on(ace.click_event+'.ace.menu', '.menu-toggler', function(e){
		var btn = $(this);
		var sidebar = $(btn.attr('data-target'));
		if(sidebar.length == 0) return;
		
		e.preventDefault();
				
		sidebar.toggleClass('display');
		btn.toggleClass('display');
		
		var click_event = ace.click_event+'.ace.autohide';
		var auto_hide = sidebar.attr('data-auto-hide') === 'true';

		if( btn.hasClass('display') ) {
			//hide menu if clicked outside of it!
			if(auto_hide) {
				$(document).on(click_event, function(ev) {
					if( sidebar.get(0) == ev.target || $.contains(sidebar.get(0), ev.target) ) {
						ev.stopPropagation();
						return;
					}

					sidebar.removeClass('display');
					btn.removeClass('display');
					$(document).off(click_event);
				})
			}

			if(sidebar.attr('data-sidebar-scroll') == 'true') sidebar.ace_sidebar_scroll('reset');
		}
		else {
			if(auto_hide) $(document).off(click_event);
		}

		return false;
	})
	//sidebar collapse/expand button
	.on(ace.click_event+'.ace.menu', '.sidebar-collapse', function(e){
		
		var target = $(this).attr('data-target'), $sidebar = null;
		if(target) $sidebar = $(target);
		if($sidebar == null || $sidebar.length == 0) $sidebar = $(this).closest('.sidebar');
		if($sidebar.length == 0) return;

		e.preventDefault();
		$sidebar.ace_sidebar('toggleMenu', this);
	})
	//this button is used in `mobile_style = 3` responsive menu style to expand minimized sidebar
	.on(ace.click_event+'.ace.menu', '.sidebar-expand', function(e){
		var target = $(this).attr('data-target'), $sidebar = null;
		if(target) $sidebar = $(target);
		if($sidebar == null || $sidebar.length == 0) $sidebar = $(this).closest('.sidebar');
		if($sidebar.length == 0) return;	
	
		var btn = this;
		e.preventDefault();
		$sidebar.ace_sidebar('toggleResponsive', this);
		
		var click_event = ace.click_event+'.ace.autohide';
		if($sidebar.attr('data-auto-hide') === 'true') {
			if( $sidebar.hasClass('responsive-max') ) {
				$(document).on(click_event, function(ev) {
					if( $sidebar.get(0) == ev.target || $.contains($sidebar.get(0), ev.target) ) {
						ev.stopPropagation();
						return;
					}

					$sidebar.ace_sidebar('toggleResponsive', btn);
					$(document).off(click_event);
				})
			}
			else {
				$(document).off(click_event);
			}
		}
	})

	
	$.fn.ace_sidebar = function (option, value) {
		var method_call;

		var $set = this.each(function () {
			var $this = $(this);
			var data = $this.data('ace_sidebar');
			var options = typeof option === 'object' && option;

			if (!data) $this.data('ace_sidebar', (data = new Sidebar(this, options)));
			if (typeof option === 'string' && typeof data[option] === 'function') {
				if(value instanceof Array) method_call = data[option].apply(data, value);
				else method_call = data[option](value);
			}
		});

		return (method_call === undefined) ? $set : method_call;
	};
	
	
	$.fn.ace_sidebar.defaults = {
		'duration': 300
    }


})(window.jQuery);
/**
 <b>Scrollbars for sidebar</b>. This approach can <span class="text-danger">only</span> be used on <u>fixed</u> sidebar.
 It doesn't use <u>"overflow:hidden"</u> CSS property and therefore can be used with <u>.hover</u> submenus and minimized sidebar.
 Except when in mobile view and menu toggle button is not in the navbar.
*/

(function($ , undefined) {
	//if( !$.fn.ace_scroll ) return;

	var old_safari = ace.vars['safari'] && navigator.userAgent.match(/version\/[1-5]/i)
	//NOTE
	//Safari on windows has not been updated for a long time.
	//And it has a problem when sidebar is fixed & scrollable and there is a CSS3 animation inside page content.
	//Very probably windows users of safari have migrated to another browser by now!

	var is_element_pos =
	'getComputedStyle' in window ?
	//el.offsetHeight is used to force redraw and recalculate 'el.style.position' esp. for webkit!
	function(el, pos) { el.offsetHeight; return window.getComputedStyle(el).position == pos }
	:
	function(el, pos) { el.offsetHeight; return $(el).css('position') == pos }
	
		
	function Sidebar_Scroll(sidebar , settings) {
		var self = this;

		var $window = $(window);
		var $sidebar = $(sidebar),
			$nav = $sidebar.find('.nav-list'),
			$toggle = $sidebar.find('.sidebar-toggle').eq(0),
			$shortcuts = $sidebar.find('.sidebar-shortcuts').eq(0);
			
		var nav = $nav.get(0);
		if(!nav) return;
		
		
		var attrib_values = ace.helper.getAttrSettings(sidebar, $.fn.ace_sidebar_scroll.defaults);
		this.settings = $.extend({}, $.fn.ace_sidebar_scroll.defaults, settings, attrib_values);
		var scroll_to_active = self.settings.scroll_to_active;
	
	
		var ace_sidebar = $sidebar.ace_sidebar('ref');
		$sidebar.attr('data-sidebar-scroll', 'true');
			
		
		var scroll_div = null,
			scroll_content = null,
			scroll_content_div = null,
			bar = null,
			track = null,
			ace_scroll = null;


		this.is_scrolling = false;
		var _initiated = false;
		this.sidebar_fixed = is_element_pos(sidebar, 'fixed');
		
		var $avail_height, $content_height;

		
		var available_height = function() {
			//available window space
			var offset = $nav.parent().offset();//because `$nav.offset()` considers the "scrolled top" amount as well
			if(self.sidebar_fixed) offset.top -= ace.helper.scrollTop();

			return $window.innerHeight() - offset.top - ( self.settings.include_toggle ? 0 : $toggle.outerHeight() );
		}
		var content_height = function() {
			return nav.clientHeight;//we don't use nav.scrollHeight here, because hover submenus are considered in calculating scrollHeight despite position=absolute!
		}

		
		
		var initiate = function(on_page_load) {
			if( _initiated ) return;
			if( !self.sidebar_fixed ) return;//eligible??
			//return if we want scrollbars only on "fixed" sidebar and sidebar is not "fixed" yet!

			//initiate once
			$nav.wrap('<div class="nav-wrap-up pos-rel" />');
			$nav.after('<div><div></div></div>');

			$nav.wrap('<div class="nav-wrap" />');
			if(!self.settings.include_toggle) $toggle.css({'z-index': 1});
			if(!self.settings.include_shortcuts) $shortcuts.css({'z-index': 99});

			scroll_div = $nav.parent().next()
			.ace_scroll({
				size: available_height(),
				//reset: true,
				mouseWheelLock: true,
				hoverReset: false,
				dragEvent: true,
				styleClass: self.settings.scroll_style,
				touchDrag: false//disable touch drag event on scrollbars, we'll add a custom one later
			})
			.closest('.ace-scroll').addClass('nav-scroll');
			
			ace_scroll = scroll_div.data('ace_scroll');

			scroll_content = scroll_div.find('.scroll-content').eq(0);
			scroll_content_div = scroll_content.find(' > div').eq(0);
			
			track = $(ace_scroll.get_track());
			bar = track.find('.scroll-bar').eq(0);

			if(self.settings.include_shortcuts && $shortcuts.length != 0) {
				$nav.parent().prepend($shortcuts).wrapInner('<div />');
				$nav = $nav.parent();
			}
			if(self.settings.include_toggle && $toggle.length != 0) {
				$nav.append($toggle);
				$nav.closest('.nav-wrap').addClass('nav-wrap-t');//it just helps to remove toggle button's top border and restore li:last-child's bottom border
			}

			$nav.css({position: 'relative'});
			if( self.settings.scroll_outside == true ) scroll_div.addClass('scrollout');
			
			nav = $nav.get(0);
			nav.style.top = 0;
			scroll_content.on('scroll.nav', function() {
				nav.style.top = (-1 * this.scrollTop) + 'px';
			});
			
			//mousewheel library available?
			$nav.on(!!$.event.special.mousewheel ? 'mousewheel.ace_scroll' : 'mousewheel.ace_scroll DOMMouseScroll.ace_scroll', function(event){
				if( !self.is_scrolling || !ace_scroll.is_active() ) {
					return !self.settings.lock_anyway;
				}
				//transfer $nav's mousewheel event to scrollbars
				return scroll_div.trigger(event);
			});
			
			$nav.on('mouseenter.ace_scroll', function() {
				track.addClass('scroll-hover');
			}).on('mouseleave.ace_scroll', function() {
				track.removeClass('scroll-hover');
			});


			/**
			$(document.body).on('touchmove.nav', function(event) {
				if( self.is_scrolling && $.contains(sidebar, event.target) ) {
					event.preventDefault();
					return false;
				}
			})
			*/

			//you can also use swipe event in a similar way //swipe.nav
			var content = scroll_content.get(0);
			$nav.on('ace_drag.nav', function(event) {
				if( !self.is_scrolling || !ace_scroll.is_active() ) {
					event.retval.cancel = true;
					return;
				}
				
				//if submenu hover is being scrolled let's cancel sidebar scroll!
				if( $(event.target).closest('.can-scroll').length != 0 ) {
					event.retval.cancel = true;
					return;
				}

				if(event.direction == 'up' || event.direction == 'down') {
					
					ace_scroll.move_bar(true);
					
					var distance = event.dy;
					
					distance = parseInt(Math.min($avail_height, distance))
					if(Math.abs(distance) > 2) distance = distance * 2;
					
					if(distance != 0) {
						content.scrollTop = content.scrollTop + distance;
						nav.style.top = (-1 * content.scrollTop) + 'px';
					}
				}
			});
			

			//for drag only
			if(self.settings.smooth_scroll) {
				$nav
				.on('touchstart.nav MSPointerDown.nav pointerdown.nav', function(event) {
					$nav.css('transition-property', 'none');
					bar.css('transition-property', 'none');
				})
				.on('touchend.nav touchcancel.nav MSPointerUp.nav MSPointerCancel.nav pointerup.nav pointercancel.nav', function(event) {
					$nav.css('transition-property', 'top');
					bar.css('transition-property', 'top');
				});
			}
			
			

			if(old_safari && !self.settings.include_toggle) {
				var toggle = $toggle.get(0);
				if(toggle) scroll_content.on('scroll.safari', function() {
					ace.helper.redraw(toggle);
				});
			}

			_initiated = true;

			//if the active item is not visible, scroll down so that it becomes visible
			//only the first time, on page load
			if(on_page_load == true) {
				self.reset();//try resetting at first

				if( scroll_to_active ) {
					self.scroll_to_active();
				}
				scroll_to_active = false;
			}
			
			
			
			if( typeof self.settings.smooth_scroll === 'number' && self.settings.smooth_scroll > 0) {
				$nav.css({'transition-property': 'top', 'transition-duration': (self.settings.smooth_scroll / 1000).toFixed(2)+'s'})
				bar.css({'transition-property': 'top', 'transition-duration': (self.settings.smooth_scroll / 1500).toFixed(2)+'s'})
				
				scroll_div
				.on('drag.start', function(e) {
					e.stopPropagation();
					$nav.css('transition-property', 'none')
				})
				.on('drag.end', function(e) {
					e.stopPropagation();
					$nav.css('transition-property', 'top')
				});
			}
			
			if(ace.vars['android']) {
				//force hide address bar, because its changes don't trigger window resize and become kinda ugly
				var val = ace.helper.scrollTop();
				if(val < 2) {
					window.scrollTo( val, 0 );
					setTimeout( function() {
						self.reset();
					}, 20 );
				}
				
				var last_height = ace.helper.winHeight() , new_height;
				$(window).on('scroll.ace_scroll', function() {
					if(self.is_scrolling && ace_scroll.is_active()) {
						new_height = ace.helper.winHeight();
						if(new_height != last_height) {
							last_height = new_height;
							self.reset();
						}
					}
				});
			}
		}
		
		
		
		
		this.scroll_to_active = function() {
			if( !ace_scroll || !ace_scroll.is_active() ) return;
			try {
				//sometimes there's no active item or not 'offsetTop' property
				var $active;
				
				var vars = ace_sidebar['vars']()

				var nav_list = $sidebar.find('.nav-list')
				if(vars['minimized'] && !vars['collapsible']) {
					$active = nav_list.find('> .active')
				}
				else {
					$active = $nav.find('> .active.hover')
					if($active.length == 0)	$active = $nav.find('.active:not(.open)')
				}

			
				var top = $active.outerHeight();
				nav_list = nav_list.get(0);
				var active = $active.get(0);
				while(active != nav_list) {
					top += active.offsetTop;
					active = active.parentNode;
				}

				var scroll_amount = top - scroll_div.height();
				if(scroll_amount > 0) {
					nav.style.top = -scroll_amount + 'px';
					scroll_content.scrollTop(scroll_amount);
				}
			}catch(e){}
		}
		
		
		
		this.reset = function(recalc) {
			if(recalc === true) {
				this.sidebar_fixed = is_element_pos(sidebar, 'fixed');
			}
			
			if( !this.sidebar_fixed ) {
				this.disable();
				return;//eligible??
			}

			//return if we want scrollbars only on "fixed" sidebar and sidebar is not "fixed" yet!

			if( !_initiated ) initiate();
			//initiate scrollbars if not yet
			
			var vars = ace_sidebar['vars']();
			

			//enable if:
			//menu is not collapsible mode (responsive navbar-collapse mode which has default browser scrollbar)
			//menu is not horizontal or horizontal but mobile view (which is not navbar-collapse)
			//and available height is less than nav's height
			

			var enable_scroll = !vars['collapsible'] && !vars['horizontal']
								&& ($avail_height = available_height()) < ($content_height = nav.clientHeight);
								//we don't use nav.scrollHeight here, because hover submenus are considered in calculating scrollHeight despite position=absolute!

								
			this.is_scrolling = true;
			if( enable_scroll ) {
				scroll_content_div.css({height: $content_height, width: 8});
				scroll_div.prev().css({'max-height' : $avail_height})
				ace_scroll.update({size: $avail_height})
				ace_scroll.enable();
				ace_scroll.reset();
			}
			if( !enable_scroll || !ace_scroll.is_active() ) {
				if(this.is_scrolling) this.disable();
			}
			else {
				$sidebar.addClass('sidebar-scroll');
			}
			
			//return this.is_scrolling;
		}
		
		
		
		this.disable = function() {
			this.is_scrolling = false;
			if(scroll_div) {
				scroll_div.css({'height' : '', 'max-height' : ''});
				scroll_content_div.css({height: '', width: ''});//otherwise it will have height and takes up some space even when invisible
				scroll_div.prev().css({'max-height' : ''})
				ace_scroll.disable();
			}

			if(parseInt(nav.style.top) < 0 && self.settings.smooth_scroll && $.support.transition.end) {
				$nav.one($.support.transition.end, function() {
					$sidebar.removeClass('sidebar-scroll');
					$nav.off('.trans');
				});
			} else {
				$sidebar.removeClass('sidebar-scroll');
			}

			nav.style.top = 0;
		}
		
		this.prehide = function(height_change) {
			if(!this.is_scrolling || ace_sidebar.get('minimized')) return;//when minimized submenu's toggle should have no effect
			
			if(content_height() + height_change < available_height()) {
				this.disable();
			}
			else if(height_change < 0) {
				//if content height is decreasing
				//let's move nav down while a submenu is being hidden
				var scroll_top = scroll_content.scrollTop() + height_change
				if(scroll_top < 0) return;

				nav.style.top = (-1 * scroll_top) + 'px';
			}
		}
		
		
		this._reset = function(recalc) {
			if(recalc === true) {
				this.sidebar_fixed = is_element_pos(sidebar, 'fixed');
			}
			
			if(ace.vars['webkit']) 
				setTimeout(function() { self.reset() } , 0);
			else this.reset();
		}
		
		
		this.set_hover = function() {
			if(track) track.addClass('scroll-hover');
		}
		
		this.get = function(name) {
			if(this.hasOwnProperty(name)) return this[name];
		}
		this.set = function(name, value) {
			if(this.hasOwnProperty(name)) this[name] = value;
		}
		this.ref = function() {
			//return a reference to self
			return this;
		}
		
		this.updateStyle = function(styleClass) {
			if(ace_scroll == null) return;
			ace_scroll.update({styleClass: styleClass});
		}

		
		//change scrollbar size after a submenu is hidden/shown
		//but don't change if sidebar is minimized
		$sidebar.on('hidden.ace.submenu.sidebar_scroll shown.ace.submenu.sidebar_scroll', '.submenu', function(e) {
			e.stopPropagation();

			if( !ace_sidebar.get('minimized') ) {
				//webkit has a little bit of a glitch!!!
				self._reset();
				if( e.type == 'shown' ) self.set_hover();
			}
		});

		
		initiate(true);//true = on_page_load
	}
	

	
	//reset on document and window changes
	$(document).on('settings.ace.sidebar_scroll', function(ev, event_name, event_val){
		$('.sidebar[data-sidebar-scroll=true]').each(function() {
			var $this = $(this);
			var sidebar_scroll = $this.ace_sidebar_scroll('ref');

			if( event_name == 'sidebar_collapsed' && is_element_pos(this, 'fixed') ) {
				if( $this.attr('data-sidebar-hover') == 'true' ) $this.ace_sidebar_hover('reset');
				sidebar_scroll._reset();
			}
			else if( event_name === 'sidebar_fixed' || event_name === 'navbar_fixed' ) {
				var is_scrolling = sidebar_scroll.get('is_scrolling');
				var sidebar_fixed = is_element_pos(this, 'fixed')
				sidebar_scroll.set('sidebar_fixed', sidebar_fixed);

				if(sidebar_fixed && !is_scrolling) {
					sidebar_scroll._reset();
				}
				else if( !sidebar_fixed ) {
					sidebar_scroll.disable();
				}
			}
		
		});
	});
	
	$(window).on('resize.ace.sidebar_scroll', function(){
		$('.sidebar[data-sidebar-scroll=true]').each(function() {
			var $this = $(this);
			if( $this.attr('data-sidebar-hover') == 'true' ) $this.ace_sidebar_hover('reset');
			/////////////
			var sidebar_scroll = $(this).ace_sidebar_scroll('ref');
			
			var sidebar_fixed = is_element_pos(this, 'fixed')
			sidebar_scroll.set('sidebar_fixed', sidebar_fixed);
			sidebar_scroll._reset();
		});
	})
	

	
	
	 /////////////////////////////////////////////
	 if(!$.fn.ace_sidebar_scroll) {
	  $.fn.ace_sidebar_scroll = function (option, value) {
		var method_call;

		var $set = this.each(function () {
			var $this = $(this);
			var data = $this.data('ace_sidebar_scroll');
			var options = typeof option === 'object' && option;

			if (!data) $this.data('ace_sidebar_scroll', (data = new Sidebar_Scroll(this, options)));
			if (typeof option === 'string' && typeof data[option] === 'function') {
				method_call = data[option](value);
			}
		});

		return (method_call === undefined) ? $set : method_call;
	 }
	 
	 
	 $.fn.ace_sidebar_scroll.defaults = {
		'scroll_to_active': true,
		'include_shortcuts': true,
		'include_toggle': false,
		'smooth_scroll': 150,
		'scroll_outside': false,
		'scroll_style': '',
		'lock_anyway': false
     }
	 
	}

})(window.jQuery);/**
 <b>Submenu hover adjustment</b>. Automatically move up a submenu to fit into screen when some part of it goes beneath window.
 Pass a "true" value as an argument and submenu will have native browser scrollbars when necessary.
*/

(function($ , undefined) {

 if( ace.vars['very_old_ie'] ) return;
 //ignore IE7 & below
 
 var hasTouch = ace.vars['touch'];
 var nativeScroll = ace.vars['old_ie'] || hasTouch;
 

 var is_element_pos =
	'getComputedStyle' in window ?
	//el.offsetHeight is used to force redraw and recalculate 'el.style.position' esp. for webkit!
	function(el, pos) { el.offsetHeight; return window.getComputedStyle(el).position == pos }
	:
	function(el, pos) { el.offsetHeight; return $(el).css('position') == pos }



 $(window).on('resize.sidebar.ace_hover', function() {
	$('.sidebar[data-sidebar-hover=true]').ace_sidebar_hover('update_vars').ace_sidebar_hover('reset');
 })

 $(document).on('settings.ace.ace_hover', function(e, event_name, event_val) {
	if(event_name == 'sidebar_collapsed') $('.sidebar[data-sidebar-hover=true]').ace_sidebar_hover('reset');
	else if(event_name == 'navbar_fixed') $('.sidebar[data-sidebar-hover=true]').ace_sidebar_hover('update_vars');
 })
 
 var sidebars = [];

 function Sidebar_Hover(sidebar , settings) {
	var self = this, that = this;
	
	var attrib_values = ace.helper.getAttrSettings(sidebar, $.fn.ace_sidebar_hover.defaults);
	this.settings = $.extend({}, $.fn.ace_sidebar_hover.defaults, settings, attrib_values);
	

	var $sidebar = $(sidebar), nav_list = $sidebar.find('.nav-list').get(0);
	$sidebar.attr('data-sidebar-hover', 'true');
	
	sidebars.push($sidebar);

	var sidebar_vars = {};
	var old_ie = ace.vars['old_ie'];

	
	
	var scroll_right = false;
	//scroll style class
	
	if(hasTouch) self.settings.sub_hover_delay = parseInt(Math.max(self.settings.sub_hover_delay, 2500));//for touch device, delay is at least 2.5sec

	var $window = $(window);
	//navbar used for adding extra offset from top when adjusting submenu
	var $navbar = $('.navbar').eq(0);
	var navbar_fixed = $navbar.css('position') == 'fixed';
	this.update_vars = function() {
		navbar_fixed = $navbar.css('position') == 'fixed';
	}

	self.dirty = false;
	//on window resize or sidebar expand/collapse a previously "pulled up" submenu should be reset back to its default position
	//for example if "pulled up" in "responsive-min" mode, in "fullmode" should not remain "pulled up"
	this.reset = function() {
		if( self.dirty == false ) return;
		self.dirty = false;//so don't reset is not called multiple times in a row!
	
		$sidebar.find('.submenu').each(function() {
			var $sub = $(this), li = $sub.parent();
			$sub.css({'top': '', 'bottom': '', 'max-height': ''});
			
			if($sub.hasClass('ace-scroll')) {
				$sub.ace_scroll('disable');
			}
			else {
				$sub.removeClass('sub-scroll');
			}
			 
			if( is_element_pos(this, 'absolute') ) $sub.addClass('can-scroll');
			else $sub.removeClass('can-scroll');

			li.removeClass('pull_up').find('.menu-text:first').css('margin-top', '');
		})

		$sidebar.find('.hover-show').removeClass('hover-show hover-shown hover-flip');
	}
	
	this.updateStyle = function(newStyle) {
		sub_scroll_style = newStyle;
		$sidebar.find('.submenu.ace-scroll').ace_scroll('update', {styleClass: newStyle});
	}
	this.changeDir = function(dir) {
		scroll_right = (dir === 'right');
	}
	
	
	//update submenu scrollbars on submenu hide & show

	var lastScrollHeight = -1;
	//hide scrollbars if it's going to be not needed anymore!
	if(!nativeScroll)
	$sidebar.on('hide.ace.submenu.sidebar_hover', '.submenu', function(e) {
		if(lastScrollHeight < 1) return;

		e.stopPropagation();
		var $sub = $(this).closest('.ace-scroll.can-scroll');
		if($sub.length == 0 || !is_element_pos($sub[0], 'absolute')) return;

		if($sub[0].scrollHeight - this.scrollHeight < lastScrollHeight) {
			$sub.ace_scroll('disable');
		}
	});

	
	
	
	//reset scrollbars 
	if(!nativeScroll)
	$sidebar.on('shown.ace.submenu.sidebar_hover hidden.ace.submenu.sidebar_hover', '.submenu', function(e) {
		if(lastScrollHeight < 1) return;
	
		var $sub = $(this).closest('.ace-scroll.can-scroll');
		if($sub.length == 0 || !is_element_pos($sub[0], 'absolute') ) return;
		
		var sub_h = $sub[0].scrollHeight;
		
		if(lastScrollHeight > 14 && sub_h - lastScrollHeight > 4) {
			$sub.ace_scroll('enable').ace_scroll('reset');//don't update track position
		}
		else {
			$sub.ace_scroll('disable');
		}
	});


	///////////////////////


	var currentScroll = -1;

	//some mobile browsers don't have mouseenter
	var event_1 = !hasTouch ? 'mouseenter.sub_hover' : 'touchstart.sub_hover';// pointerdown.sub_hover';
	var event_2 = !hasTouch ? 'mouseleave.sub_hover' : 'touchend.sub_hover touchcancel.sub_hover';// pointerup.sub_hover pointercancel.sub_hover';
	
	$sidebar.on(event_1, '.nav-list li, .sidebar-shortcuts', function (e) {
		sidebar_vars = $sidebar.ace_sidebar('vars');
		
	
		//ignore if collapsible mode (mobile view .navbar-collapse) so it doesn't trigger submenu movements
		//or return if horizontal but not mobile_view (style 1&3)
		if( sidebar_vars['collapsible'] /**|| sidebar_vars['horizontal']*/ ) return;
		
		var $this = $(this);

		var shortcuts = false;
		var has_hover = $this.hasClass('hover');
		
		var sub = $this.find('> .submenu').get(0);
		if( !(sub || ((this.parentNode == nav_list || has_hover || (shortcuts = $this.hasClass('sidebar-shortcuts'))) /**&& sidebar_vars['minimized']*/)) ) {
			if(sub) $(sub).removeClass('can-scroll');
			return;//include .compact and .hover state as well?
		}
		
		var target_element = sub, is_abs = false;
		if( !target_element && this.parentNode == nav_list ) target_element = $this.find('> a > .menu-text').get(0);
		if( !target_element && shortcuts ) target_element = $this.find('.sidebar-shortcuts-large').get(0);
		if( (!target_element || !(is_abs = is_element_pos(target_element, 'absolute'))) && !has_hover ) {
			if(sub) $(sub).removeClass('can-scroll');
			return;
		}
		
		
		var sub_hide = getSubHide(this);
		//var show_sub = false;

		if(sub) {
		 if(is_abs) {
			self.dirty = true;
			
			var newScroll = ace.helper.scrollTop();
			//if submenu is becoming visible for first time or document has been scrolled, then adjust menu
			if( !sub_hide.is_visible() || (!hasTouch && newScroll != currentScroll) || old_ie ) {
				//try to move/adjust submenu if the parent is a li.hover or if submenu is minimized
				//if( is_element_pos(sub, 'absolute') ) {//for example in small device .hover > .submenu may not be absolute anymore!
					$(sub).addClass('can-scroll');
					//show_sub = true;
					if(!old_ie && !hasTouch) adjust_submenu.call(this, sub);
					else {
						//because ie8 needs some time for submenu to be displayed and real value of sub.scrollHeight be kicked in
						var that = this;
						setTimeout(function() {	adjust_submenu.call(that, sub) }, 0)
					}
				//}
				//else $(sub).removeClass('can-scroll');
			}
			currentScroll = newScroll;
		 }
		 else {
			$(sub).removeClass('can-scroll');
		 }
		}
		//if(show_sub) 
		sub_hide.show();
		
	 }).on(event_2, '.nav-list li, .sidebar-shortcuts', function (e) {
		sidebar_vars = $sidebar.ace_sidebar('vars');
		
		if( sidebar_vars['collapsible'] /**|| sidebar_vars['horizontal']*/ ) return;

		if( !$(this).hasClass('hover-show') ) return;

		getSubHide(this).hideDelay();
	 });
	 
	
	function subHide(li_sub) {
		var self = li_sub, $self = $(self);
		var timer = null;
		var visible = false;
		
		this.show = function() {
			if(timer != null) clearTimeout(timer);
			timer = null;		

			$self.addClass('hover-show hover-shown');
			visible = true;

			//let's hide .hover-show elements that are not .hover-shown anymore (i.e. marked for hiding in hideDelay)
			for(var i = 0; i < sidebars.length ; i++)
			{
			  sidebars[i].find('.hover-show').not('.hover-shown').each(function() {
				getSubHide(this).hide();
			  })
			}
		}
		
		this.hide = function() {
			visible = false;
			
			$self.removeClass('hover-show hover-shown hover-flip');
			
			if(timer != null) clearTimeout(timer);
			timer = null;
			
			var sub = $self.find('> .submenu').get(0);
			if(sub) getSubScroll(sub, 'hide');
		}
		
		this.hideDelay = function(callback) {
			if(timer != null) clearTimeout(timer);
			
			$self.removeClass('hover-shown');//somehow marked for hiding
			
			timer = setTimeout(function() {
				visible = false;
				$self.removeClass('hover-show hover-flip');
				timer = null;
				
				var sub = $self.find('> .submenu').get(0);
				if(sub) getSubScroll(sub, 'hide');
				
				if(typeof callback === 'function') callback.call(this);
			}, that.settings.sub_hover_delay);
		}
		
		this.is_visible = function() {
			return visible;
		}
	}
	function getSubHide(el) {
		var sub_hide = $(el).data('subHide');
		if(!sub_hide) $(el).data('subHide', (sub_hide = new subHide(el)));
		return sub_hide;
	}
	
	
	function getSubScroll(el, func) {
		var sub_scroll = $(el).data('ace_scroll');
		if(!sub_scroll) return false;
		if(typeof func === 'string') {
			sub_scroll[func]();
			return true;
		}
		return sub_scroll;
	}	
	
	function adjust_submenu(sub) {
		var $li = $(this);
		var $sub = $(sub);
		sub.style.top = '';
		sub.style.bottom = '';


		var menu_text = null
		if( sidebar_vars['minimized'] && (menu_text = $li.find('.menu-text').get(0)) ) {
			//2nd level items don't have .menu-text
			menu_text.style.marginTop = '';
		}

		var scroll = ace.helper.scrollTop();
		var navbar_height = 0;

		var $scroll = scroll;
		
		if( navbar_fixed ) {
			navbar_height = sidebar.offsetTop;//$navbar.height();
			$scroll += navbar_height + 1;
			//let's avoid our submenu from going below navbar
			//because of chrome z-index stacking issue and firefox's normal .submenu over fixed .navbar flicker issue
		}




		var off = $li.offset();
		off.top = parseInt(off.top);
		
		var extra = 0, parent_height;
		
		sub.style.maxHeight = '';//otherwise scrollHeight won't be consistent in consecutive calls!?
		var sub_h = sub.scrollHeight;
		var parent_height = $li.height();
		if(menu_text) {
			extra = parent_height;
			off.top += extra;
		}
		var sub_bottom = parseInt(off.top + sub_h)

		var move_up = 0;
		var winh = $window.height();


		//if the bottom of menu is going to go below visible window

		var top_space = parseInt(off.top - $scroll - extra);//available space on top
		var win_space = winh;//available window space
		
		var horizontal = sidebar_vars['horizontal'], horizontal_sub = false;
		if(horizontal && this.parentNode == nav_list) {
			move_up = 0;//don't move up first level submenu in horizontal mode
			off.top += $li.height();
			horizontal_sub = true;//first level submenu
		}

		if(!horizontal_sub && (move_up = (sub_bottom - (winh + scroll))) >= 0 ) {
			//don't move up more than available space
			move_up = move_up < top_space ? move_up : top_space;

			//move it up a bit more if there's empty space
			if(move_up == 0) move_up = 20;
			if(top_space - move_up > 10) {
				move_up += parseInt(Math.min(25, top_space - move_up));
			}


			//move it down if submenu's bottom is going above parent LI
			if(off.top + (parent_height - extra) > (sub_bottom - move_up)) {
				move_up -= (off.top + (parent_height - extra) - (sub_bottom - move_up));
			}

			if(move_up > 0) {
				sub.style.top = -(move_up) + 'px';
				if( menu_text ) {
					menu_text.style.marginTop = -(move_up) + 'px';
				}
			}
		}
		if(move_up < 0) move_up = 0;//when it goes below
		
		var pull_up = move_up > 0 && move_up > parent_height - 20;
		if(pull_up) {
			$li.addClass('pull_up');
		}
		else $li.removeClass('pull_up');
		
		
		//flip submenu if out of window width
		if(horizontal) {
			if($li.parent().parent().hasClass('hover-flip')) $li.addClass('hover-flip');//if a parent is already flipped, flip it then!
			else {
				var sub_off = $sub.offset();
				var sub_w = $sub.width();
				var win_w = $window.width();
				if(sub_off.left + sub_w > win_w) {
					$li.addClass('hover-flip');
				}
			}
		}


		//don't add scrollbars if it contains .hover menus
		var has_hover = $li.hasClass('hover') && !sidebar_vars['mobile_view'];
		if(has_hover && $sub.find('> li > .submenu').length > 0) return;

	
		//if(  ) {
			var scroll_height = (win_space - (off.top - scroll)) + (move_up);
			//if after scroll, the submenu is above parent LI, then move it down
			var tmp = move_up - scroll_height;
			if(tmp > 0 && tmp < parent_height) scroll_height += parseInt(Math.max(parent_height, parent_height - tmp));

			scroll_height -= 5;
			
			if(scroll_height < 90) {
				return;
			}
			
			var ace_scroll = false;
			if(!nativeScroll) {
				ace_scroll = getSubScroll(sub);
				if(ace_scroll == false) {
					$sub.ace_scroll({
						//hideOnIdle: true,
						observeContent: true,
						detached: true,
						updatePos: false,
						reset: true,
						mouseWheelLock: true,
						styleClass: self.settings.sub_scroll_style
					});
					ace_scroll = getSubScroll(sub);
					
					var track = ace_scroll.get_track();
					if(track) {
						//detach it from body and insert it after submenu for better and cosistent positioning
						$sub.after(track);
					}
				}
				
				ace_scroll.update({size: scroll_height});
			}
			else {
				$sub
				.addClass('sub-scroll')
				.css('max-height', (scroll_height)+'px')
			}


			lastScrollHeight = scroll_height;
			if(!nativeScroll && ace_scroll) {
				if(scroll_height > 14 && sub_h - scroll_height > 4) {
					ace_scroll.enable()
					ace_scroll.reset();
				}			
				else {
					ace_scroll.disable();
				}

				//////////////////////////////////
				var track = ace_scroll.get_track();
				if(track) {
					track.style.top = -(move_up - extra - 1) + 'px';
					
					var off = $sub.position();
					var left = off.left 
					if( !scroll_right ) {
						left += ($sub.outerWidth() - ace_scroll.track_size());
					}
					else {
						left += 2;
					}
					track.style.left = parseInt(left) + 'px';
					
					if(horizontal_sub) {//first level submenu
						track.style.left = parseInt(left - 2) + 'px';
						track.style.top = parseInt(off.top) + (menu_text ? extra - 2 : 0) + 'px';
					}
				}
			}
		//}


		//again force redraw for safari!
		if( ace.vars['safari'] ) {
			ace.helper.redraw(sub)
		}
   }

}
 
 
 
 /////////////////////////////////////////////
 $.fn.ace_sidebar_hover = function (option, value) {
	var method_call;

	var $set = this.each(function () {
		var $this = $(this);
		var data = $this.data('ace_sidebar_hover');
		var options = typeof option === 'object' && option;

		if (!data) $this.data('ace_sidebar_hover', (data = new Sidebar_Hover(this, options)));
		if (typeof option === 'string' && typeof data[option] === 'function') {
			method_call = data[option](value);
		}
	});

	return (method_call === undefined) ? $set : method_call;
 }
 
  $.fn.ace_sidebar_hover.defaults = {
	'sub_sub_hover_delay': 750,
	'sub_scroll_style': 'no-track scroll-thin'
 }
 

})(window.jQuery);

/**
 <b>Settings box</b>. It's good for demo only. You don't need this.
*/
(function($ , undefined) {

 $('#ace-settings-btn').on(ace.click_event, function(e){
	e.preventDefault();

	$(this).toggleClass('open');
	$('#ace-settings-box').toggleClass('open');
 })

 $('#ace-settings-navbar').on('click', function(){
	ace.settings.navbar_fixed(null, this.checked);//@ ace-extra.js
	//$(window).triggerHandler('resize.navbar');

	//force redraw?
	//if(ace.vars['webkit']) ace.helper.redraw(document.body);
 }).each(function(){this.checked = ace.settings.is('navbar', 'fixed')})

 $('#ace-settings-sidebar').on('click', function(){
	ace.settings.sidebar_fixed(null, this.checked);//@ ace-extra.js

	//if(ace.vars['webkit']) ace.helper.redraw(document.body);
 }).each(function(){this.checked = ace.settings.is('sidebar', 'fixed')})

 $('#ace-settings-breadcrumbs').on('click', function(){
	ace.settings.breadcrumbs_fixed(null, this.checked);//@ ace-extra.js

	//if(ace.vars['webkit']) ace.helper.redraw(document.body);
 }).each(function(){this.checked = ace.settings.is('breadcrumbs', 'fixed')})

 $('#ace-settings-add-container').on('click', function(){
	ace.settings.main_container_fixed(null, this.checked);//@ ace-extra.js

	//if(ace.vars['webkit']) ace.helper.redraw(document.body);
 }).each(function(){this.checked = ace.settings.is('main-container', 'fixed')})



 $('#ace-settings-compact').on('click', function(){
	if(this.checked) {
		$('#sidebar').addClass('compact');
		var hover = $('#ace-settings-hover');
		if( hover.length > 0 ) {
			hover.removeAttr('checked').trigger('click');
		}
	}
	else {
		$('#sidebar').removeClass('compact');
		$('#sidebar[data-sidebar-scroll=true]').ace_sidebar_scroll('reset')
	}
	
	if(ace.vars['old_ie']) ace.helper.redraw($('#sidebar')[0], true);
 })/*.removeAttr('checked')*/


 $('#ace-settings-highlight').on('click', function(){
	if(this.checked) $('#sidebar .nav-list > li').addClass('highlight');
	else $('#sidebar .nav-list > li').removeClass('highlight');
	
	if(ace.vars['old_ie']) ace.helper.redraw($('#sidebar')[0]);
 })/*.removeAttr('checked')*/


 $('#ace-settings-hover').on('click', function(){
	if($('#sidebar').hasClass('h-sidebar')) return;
	if(this.checked) {
		$('#sidebar li').addClass('hover')
		.filter('.open').removeClass('open').find('> .submenu').css('display', 'none');
		//and remove .open items
	}
	else {
		$('#sidebar li.hover').removeClass('hover');

		var compact = $('#ace-settings-compact');
		if( compact.length > 0 && compact.get(0).checked ) {
			compact.trigger('click');
		}
	}
	
	$('.sidebar[data-sidebar-hover=true]').ace_sidebar_hover('reset')
	$('.sidebar[data-sidebar-scroll=true]').ace_sidebar_scroll('reset')
	
	if(ace.vars['old_ie']) ace.helper.redraw($('#sidebar')[0]);
 })/*.removeAttr('checked')*/

})(jQuery);/**
The autocomplete dropdown when typing inside search box.
<u><i class="glyphicon glyphicon-flash"></i> You don't need this. Used for demo only</u>
*/
(function($ , undefined) {

	ace.vars['US_STATES'] = ["Alabama","Alaska","Arizona","Arkansas","California","Colorado","Connecticut","Delaware","Florida","Georgia","Hawaii","Idaho","Illinois","Indiana","Iowa","Kansas","Kentucky","Louisiana","Maine","Maryland","Massachusetts","Michigan","Minnesota","Mississippi","Missouri","Montana","Nebraska","Nevada","New Hampshire","New Jersey","New Mexico","New York","North Dakota","North Carolina","Ohio","Oklahoma","Oregon","Pennsylvania","Rhode Island","South Carolina","South Dakota","Tennessee","Texas","Utah","Vermont","Virginia","Washington","West Virginia","Wisconsin","Wyoming"]
	try {
		$('#nav-search-input').bs_typeahead({
			source: ace.vars['US_STATES'],
			updater:function (item) {
				//when an item is selected from dropdown menu, focus back to input element
				$('#nav-search-input').focus();
				return item;
			}
		});
	} catch(e) {}

})(window.jQuery);/*!
 * Ace v1.3.3
 */
if("undefined"==typeof jQuery)throw new Error("Ace's JavaScript requires jQuery");!function(a,b){var c=function(c,d){function e(a){a.preventDefault(),a.stopPropagation();var b=B.offset(),c=b[p],d=v?a.pageY:a.pageX;d>c+H?(H=d-c-G+J,H>I&&(H=I)):(H=d-c-J,0>H&&(H=0)),m.update_scroll()}function f(b){b.preventDefault(),b.stopPropagation(),cb=bb=v?b.pageY:b.pageX,R=!0,a("html").off("mousemove.ace_scroll").on("mousemove.ace_scroll",g),a(S).off("mouseup.ace_scroll").on("mouseup.ace_scroll",h),B.addClass("active"),T&&m.$element.trigger("drag.start")}function g(a){a.preventDefault(),a.stopPropagation(),cb=v?a.pageY:a.pageX,cb-bb+H>I?cb=bb+I-H:0>cb-bb+H&&(cb=bb-H),H+=cb-bb,bb=cb,0>H?H=0:H>I&&(H=I),m.update_scroll()}function h(b){b.preventDefault(),b.stopPropagation(),R=!1,a("html").off(".ace_scroll"),a(S).off(".ace_scroll"),B.removeClass("active"),T&&m.$element.trigger("drag.end"),x&&X&&!Z&&j()}function i(a){var b=+new Date;if($&&b-eb>1e3){var c=A[u];_!=c&&(_=c,ab=!0,m.reset(!0)),eb=b}x&&X&&(null!=db&&(clearTimeout(db),db=null),B.addClass("not-idle"),Z||1!=a||j())}function j(){null!=db&&(clearTimeout(db),db=null),db=setTimeout(function(){db=null,B.removeClass("not-idle")},Y)}function k(){B.css("visibility","hidden").addClass("scroll-hover"),O=v?parseInt(B.outerWidth())||0:parseInt(B.outerHeight())||0,B.css("visibility","").removeClass("scroll-hover")}function l(){if(W!==!1){var a=z.offset(),b=a.left,c=a.top;v?N||(b+=z.outerWidth()-O):N||(c+=z.outerHeight()-O),W===!0?B.css({top:parseInt(c),left:parseInt(b)}):"left"===W?B.css("left",parseInt(b)):"top"===W&&B.css("top",parseInt(c))}}var m=this,n=ace.helper.getAttrSettings(c,a.fn.ace_scroll.defaults),o=a.extend({},a.fn.ace_scroll.defaults,d,n);this.size=0,this.lock=!1,this.lock_anyway=!1,this.$element=a(c),this.element=c;var p,q,r,s,t,u,v=!0,w=!1,x=!1,y=!1,z=null,A=null,B=null,C=null,D=null,E=null,F=null,G=0,H=0,I=0,J=0,K=!0,L=!1,M="",N=!1,O=0,P=1,Q=!1,R=!1,S="onmouseup"in window?window:"html",T=o.dragEvent||!1,U=d.scrollEvent||!1,V=o.detached||!1,W=o.updatePos||!1,X=o.hideOnIdle||!1,Y=o.hideDelay||1500,Z=!1,$=o.observeContent||!1,_=0,ab=!0;this.create=function(b){if(!y){b&&(o=a.extend({},a.fn.ace_scroll.defaults,b)),this.size=parseInt(this.$element.attr("data-size"))||o.size||200,v=!o.horizontal,p=v?"top":"left",q=v?"height":"width",r=v?"maxHeight":"maxWidth",s=v?"clientHeight":"clientWidth",t=v?"scrollTop":"scrollLeft",u=v?"scrollHeight":"scrollWidth",this.$element.addClass("ace-scroll"),"static"==this.$element.css("position")?(Q=this.element.style.position,this.element.style.position="relative"):Q=!1;var c=null;V?c=a('<div class="scroll-track scroll-detached"><div class="scroll-bar"></div></div>').appendTo("body"):(this.$element.wrapInner('<div class="scroll-content" />'),this.$element.prepend('<div class="scroll-track"><div class="scroll-bar"></div></div>')),z=this.$element,V||(z=this.$element.find(".scroll-content").eq(0)),v||z.wrapInner("<div />"),A=z.get(0),V?(B=c,l()):B=this.$element.find(".scroll-track").eq(0),C=B.find(".scroll-bar").eq(0),D=B.get(0),E=C.get(0),F=E.style,v||B.addClass("scroll-hz"),o.styleClass&&(M=o.styleClass,B.addClass(M),N=!!M.match(/scroll\-left|scroll\-top/)),0==O&&(B.show(),k()),B.hide(),B.on("mousedown",e),C.on("mousedown",f),z.on("scroll",function(){K&&(H=parseInt(Math.round(this[t]*P)),F[p]=H+"px"),K=!1,U&&this.$element.trigger("scroll",[A])}),o.mouseWheel&&(this.lock=o.mouseWheelLock,this.lock_anyway=o.lockAnyway,this.$element.on(a.event.special.mousewheel?"mousewheel.ace_scroll":"mousewheel.ace_scroll DOMMouseScroll.ace_scroll",function(b){if(!w){if(i(!0),!x)return!m.lock_anyway;R&&(R=!1,a("html").off(".ace_scroll"),a(S).off(".ace_scroll"),T&&m.$element.trigger("drag.end")),b.deltaY=b.deltaY||0;var c=b.deltaY>0||b.originalEvent.detail<0||b.originalEvent.wheelDelta>0?1:-1,d=!1,e=A[s],f=A[t];m.lock||(d=-1==c?A[u]<=f+e:0==f),m.move_bar(!0);var g=parseInt(e/8);return 80>g&&(g=80),g>m.size&&(g=m.size),g+=1,A[t]=f-c*g,d&&!m.lock_anyway}}));var d=ace.vars.touch&&"ace_drag"in a.event.special&&o.touchDrag;if(d){var g="",h=d?"ace_drag":"swipe";this.$element.on(h+".ace_scroll",function(a){if(w)return void(a.retval.cancel=!0);if(i(!0),!x)return void(a.retval.cancel=this.lock_anyway);if(g=a.direction,v&&("up"==g||"down"==g)||!v&&("left"==g||"right"==g)){var b=v?a.dy:a.dx;0!=b&&(Math.abs(b)>20&&d&&(b=2*b),m.move_bar(!0),A[t]=A[t]+b)}})}X&&B.addClass("idle-hide"),$&&B.on("mouseenter.ace_scroll",function(){Z=!0,i(!1)}).on("mouseleave.ace_scroll",function(){Z=!1,0==R&&j()}),this.$element.on("mouseenter.ace_scroll touchstart.ace_scroll",function(){ab=!0,$?i(!0):o.hoverReset&&m.reset(!0),B.addClass("scroll-hover")}).on("mouseleave.ace_scroll touchend.ace_scroll",function(){B.removeClass("scroll-hover")}),v||z.children(0).css(q,this.size),z.css(r,this.size),w=!1,y=!0}},this.is_active=function(){return x},this.is_enabled=function(){return!w},this.move_bar=function(a){K=a},this.get_track=function(){return D},this.reset=function(a){if(!w){y||this.create();var b=this.size;if(!a||ab){if(ab=!1,V){var c=parseInt(Math.round((parseInt(z.css("border-top-width"))+parseInt(z.css("border-bottom-width")))/2.5));b-=c}var d=v?A[u]:b;if(v&&0==d||!v&&0==this.element.scrollWidth)return void B.removeClass("scroll-active");var e=v?b:A.clientWidth;v||z.children(0).css(q,b),z.css(r,this.size),d>e?(x=!0,B.css(q,e).show(),P=parseFloat((e/d).toFixed(5)),G=parseInt(Math.round(e*P)),J=parseInt(Math.round(G/2)),I=e-G,H=parseInt(Math.round(A[t]*P)),F[q]=G+"px",F[p]=H+"px",B.addClass("scroll-active"),0==O&&k(),L||(o.reset&&(A[t]=0,F[p]=0),L=!0),V&&l()):(x=!1,B.hide(),B.removeClass("scroll-active"),z.css(r,""))}}},this.disable=function(){A[t]=0,F[p]=0,w=!0,x=!1,B.hide(),this.$element.addClass("scroll-disabled"),B.removeClass("scroll-active"),z.css(r,"")},this.enable=function(){w=!1,this.$element.removeClass("scroll-disabled")},this.destroy=function(){x=!1,w=!1,y=!1,this.$element.removeClass("ace-scroll scroll-disabled scroll-active"),this.$element.off(".ace_scroll"),V||(v||z.find("> div").children().unwrap(),z.children().unwrap(),z.remove()),B.remove(),Q!==!1&&(this.element.style.position=Q),null!=db&&(clearTimeout(db),db=null)},this.modify=function(b){b&&(o=a.extend({},o,b)),this.destroy(),this.create(),ab=!0,this.reset(!0)},this.update=function(c){c&&(o=a.extend({},o,c)),this.size=c.size||this.size,this.lock=c.mouseWheelLock||this.lock,this.lock_anyway=c.lockAnyway||this.lock_anyway,c.styleClass!=b&&(M&&B.removeClass(M),M=c.styleClass,M&&B.addClass(M),N=!!M.match(/scroll\-left|scroll\-top/))},this.start=function(){A[t]=0},this.end=function(){A[t]=A[u]},this.hide=function(){B.hide()},this.show=function(){B.show()},this.update_scroll=function(){K=!1,F[p]=H+"px",A[t]=parseInt(Math.round(H/P))};var bb=-1,cb=-1,db=null,eb=0;return this.track_size=function(){return 0==O&&k(),O},this.create(),ab=!0,this.reset(!0),_=A[u],this};a.fn.ace_scroll=function(d,e){var f,g=this.each(function(){var b=a(this),g=b.data("ace_scroll"),h="object"==typeof d&&d;g||b.data("ace_scroll",g=new c(this,h)),"string"==typeof d&&(f=g[d](e))});return f===b?g:f},a.fn.ace_scroll.defaults={size:200,horizontal:!1,mouseWheel:!0,mouseWheelLock:!1,lockAnyway:!1,styleClass:!1,observeContent:!1,hideOnIdle:!1,hideDelay:1500,hoverReset:!0,reset:!1,dragEvent:!1,touchDrag:!0,touchSwipe:!1,scrollEvent:!1,detached:!1,updatePos:!0}}(window.jQuery),function(a,b){var c=function(b,c){var d=ace.helper.getAttrSettings(b,a.fn.ace_colorpicker.defaults),e=a.extend({},a.fn.ace_colorpicker.defaults,c,d),f=a(b),g="",h="",i=null,j=[];f.addClass("hide").find("option").each(function(){var a="colorpick-btn",b=this.value.replace(/[^\w\s,#\(\)\.]/g,"");this.value!=b&&(this.value=b),this.selected&&(a+=" selected",h=b),j.push(b),g+='<li><a class="'+a+'" href="#" style="background-color:'+b+';" data-color="'+b+'"></a></li>'}).end().on("change.color",function(){f.next().find(".btn-colorpicker").css("background-color",this.value)}).after('<div class="dropdown dropdown-colorpicker">		<a data-toggle="dropdown" class="dropdown-toggle" '+(e.auto_pos?'data-position="auto"':"")+' href="#"><span class="btn-colorpicker" style="background-color:'+h+'"></span></a><ul class="dropdown-menu'+(e.caret?" dropdown-caret":"")+(e.pull_right?" dropdown-menu-right":"")+'">'+g+"</ul></div>");var k=f.next().find(".dropdown-menu");k.on(ace.click_event,function(b){var c=a(b.target);if(!c.is(".colorpick-btn"))return!1;i&&i.removeClass("selected"),i=c,i.addClass("selected");var d=i.data("color");return f.val(d).trigger("change"),b.preventDefault(),!0}),i=f.next().find("a.selected"),this.pick=function(c,d){if("number"==typeof c){if(c>=j.length)return;b.selectedIndex=c,k.find("a:eq("+c+")").trigger(ace.click_event)}else if("string"==typeof c){var e=c.replace(/[^\w\s,#\(\)\.]/g,"");if(c=j.indexOf(e),-1==c&&d===!0&&(j.push(e),a("<option />").appendTo(f).val(e),a('<li><a class="colorpick-btn" href="#"></a></li>').appendTo(k).find("a").css("background-color",e).data("color",e),c=j.length-1),-1==c)return;k.find("a:eq("+c+")").trigger(ace.click_event)}},this.destroy=function(){f.removeClass("hide").off("change.color").next().remove(),j=[]}};a.fn.ace_colorpicker=function(d,e){var f,g=this.each(function(){var b=a(this),g=b.data("ace_colorpicker"),h="object"==typeof d&&d;g||b.data("ace_colorpicker",g=new c(this,h)),"string"==typeof d&&(f=g[d](e))});return f===b?g:f},a.fn.ace_colorpicker.defaults={pull_right:!1,caret:!0,auto_pos:!0}}(window.jQuery),function(a,b){var c="multiple"in document.createElement("INPUT"),d="FileList"in window,e="FileReader"in window,f="File"in window,g=function(b,c){var d=this,e=ace.helper.getAttrSettings(b,a.fn.ace_file_input.defaults);this.settings=a.extend({},a.fn.ace_file_input.defaults,c,e),this.$element=a(b),this.element=b,this.disabled=!1,this.can_reset=!0,this.$element.off("change.ace_inner_call").on("change.ace_inner_call",function(a,b){return d.disabled||b===!0?void 0:i.call(d)});var f=this.$element.closest("label").css({display:"block"}),g=0==f.length?"label":"span";this.$element.wrap("<"+g+' class="ace-file-input" />'),this.apply_settings(),this.reset_input_field()};g.error={FILE_LOAD_FAILED:1,IMAGE_LOAD_FAILED:2,THUMBNAIL_FAILED:3},g.prototype.apply_settings=function(){var b=this;this.multi=this.$element.attr("multiple")&&c,this.well_style="well"==this.settings.style,this.well_style?this.$element.parent().addClass("ace-file-multiple"):this.$element.parent().removeClass("ace-file-multiple"),this.$element.parent().find(":not(input[type=file])").remove(),this.$element.after('<span class="ace-file-container" data-title="'+this.settings.btn_choose+'"><span class="ace-file-name" data-title="'+this.settings.no_file+'">'+(this.settings.no_icon?'<i class="'+ace.vars.icon+this.settings.no_icon+'"></i>':"")+"</span></span>"),this.$label=this.$element.next(),this.$container=this.$element.closest(".ace-file-input");var e=!!this.settings.icon_remove;if(e){var f=a('<a class="remove" href="#"><i class="'+ace.vars.icon+this.settings.icon_remove+'"></i></a>').appendTo(this.$element.parent());f.on(ace.click_event,function(a){if(a.preventDefault(),!b.can_reset)return!1;var c=!0;if(b.settings.before_remove&&(c=b.settings.before_remove.call(b.element)),!c)return!1;b.reset_input();return!1})}this.settings.droppable&&d&&h.call(this)},g.prototype.show_file_list=function(b,c){var d="undefined"==typeof b?this.$element.data("ace_input_files"):b;if(d&&0!=d.length){this.well_style&&(this.$label.find(".ace-file-name").remove(),this.settings.btn_change||this.$label.addClass("hide-placeholder")),this.$label.attr("data-title",this.settings.btn_change).addClass("selected");for(var g=0;g<d.length;g++){var h="",i=!1;if("string"==typeof d[g])h=d[g];else if(f&&d[g]instanceof File)h=a.trim(d[g].name);else{if(!(d[g]instanceof Object&&d[g].hasOwnProperty("name")))continue;h=d[g].name,d[g].hasOwnProperty("type")&&(i=d[g].type),d[g].hasOwnProperty("path")||(d[g].path=d[g].name)}var k=h.lastIndexOf("\\")+1;0==k&&(k=h.lastIndexOf("/")+1),h=h.substr(k),0==i&&(i=/\.(jpe?g|png|gif|svg|bmp|tiff?)$/i.test(h)?"image":/\.(mpe?g|flv|mov|avi|swf|mp4|mkv|webm|wmv|3gp)$/i.test(h)?"video":/\.(mp3|ogg|wav|wma|amr|aac)$/i.test(h)?"audio":"file");var l={file:"fa fa-file",image:"fa fa-picture-o file-image",video:"fa fa-film file-video",audio:"fa fa-music file-audio"},m=l[i];if(this.well_style){this.$label.append('<span class="ace-file-name" data-title="'+h+'"><i class="'+ace.vars.icon+m+'"></i></span>');var n=c===!0&&f&&d[g]instanceof File?a.trim(d[g].type):"",o=e&&this.settings.thumbnail&&(n.length>0&&n.match("image")||0==n.length&&"image"==i);if(o){var p=this;a.when(j.call(this,d[g])).fail(function(a){p.settings.preview_error&&p.settings.preview_error.call(p,h,a.code)})}}else this.$label.find(".ace-file-name").attr({"data-title":h}).find(ace.vars[".icon"]).attr("class",ace.vars.icon+m)}return!0}},g.prototype.reset_input=function(){this.reset_input_ui(),this.reset_input_field()},g.prototype.reset_input_ui=function(){this.$label.attr({"data-title":this.settings.btn_choose,"class":"ace-file-container"}).find(".ace-file-name:first").attr({"data-title":this.settings.no_file,"class":"ace-file-name"}).find(ace.vars[".icon"]).attr("class",ace.vars.icon+this.settings.no_icon).prev("img").remove(),this.settings.no_icon||this.$label.find(ace.vars[".icon"]).remove(),this.$label.find(".ace-file-name").not(":first").remove(),this.reset_input_data()},g.prototype.reset_input_field=function(){this.$element.wrap("<form>").parent().get(0).reset(),this.$element.unwrap()},g.prototype.reset_input_data=function(){this.$element.data("ace_input_files")&&(this.$element.removeData("ace_input_files"),this.$element.removeData("ace_input_method"))},g.prototype.enable_reset=function(a){this.can_reset=a},g.prototype.disable=function(){this.disabled=!0,this.$element.attr("disabled","disabled").addClass("disabled")},g.prototype.enable=function(){this.disabled=!1,this.$element.removeAttr("disabled").removeClass("disabled")},g.prototype.files=function(){return a(this).data("ace_input_files")||null},g.prototype.method=function(){return a(this).data("ace_input_method")||""},g.prototype.update_settings=function(b){this.settings=a.extend({},this.settings,b),this.apply_settings()},g.prototype.loading=function(b){if(b===!1)this.$container.find(".ace-file-overlay").remove(),this.element.removeAttribute("readonly");else{var c="string"==typeof b?b:'<i class="overlay-content fa fa-spin fa-spinner orange2 fa-2x"></i>',d=this.$container.find(".ace-file-overlay");0==d.length&&(d=a('<div class="ace-file-overlay"></div>').appendTo(this.$container),d.on("click tap",function(a){return a.stopImmediatePropagation(),a.preventDefault(),!1}),this.element.setAttribute("readonly","true")),d.empty().append(c)}};var h=function(){var a=this,b=this.$element.parent();b.off("dragenter").on("dragenter",function(a){a.preventDefault(),a.stopPropagation()}).off("dragover").on("dragover",function(a){a.preventDefault(),a.stopPropagation()}).off("drop").on("drop",function(b){if(b.preventDefault(),b.stopPropagation(),!a.disabled){var c=b.originalEvent.dataTransfer,d=c.files;if(!a.multi&&d.length>1){var e=[];e.push(d[0]),d=e}return d=l.call(a,d,!0),d===!1?!1:(a.$element.data("ace_input_method","drop"),a.$element.data("ace_input_files",d),a.show_file_list(d,!0),a.$element.triggerHandler("change",[!0]),!0)}})},i=function(){var a=this.element.files||[this.element.value];return a=l.call(this,a,!1),a===!1?!1:(this.$element.data("ace_input_method","select"),this.$element.data("ace_input_files",a),this.show_file_list(a,!0),!0)},j=function(b){var c=this,d=c.$label.find(".ace-file-name:last"),e=new a.Deferred,h=function(b){d.prepend("<img class='middle' style='display:none;' />");var c=d.find("img:last").get(0);a(c).one("load",function(){i.call(null,c)}).one("error",function(){j.call(null,c)}),c.src=b},i=function(b){var f=50;"large"==c.settings.thumbnail?f=150:"fit"==c.settings.thumbnail&&(f=d.width()),d.addClass(f>50?"large":"");var h=k(b,f);if(null==h)return a(this).remove(),void e.reject({code:g.error.THUMBNAIL_FAILED});var i=h.w,j=h.h;"small"==c.settings.thumbnail&&(i=j=f),a(b).css({"background-image":"url("+h.src+")",width:i,height:j}).data("thumb",h.src).attr({src:"data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAEAAAABCAYAAAAfFcSJAAAADUlEQVQImWNgYGBgAAAABQABh6FO1AAAAABJRU5ErkJggg=="}).show(),e.resolve()},j=function(){d.find("img").remove(),e.reject({code:g.error.IMAGE_LOAD_FAILED})};if(f&&b instanceof File){var l=new FileReader;l.onload=function(a){h(a.target.result)},l.onerror=function(){e.reject({code:g.error.FILE_LOAD_FAILED})},l.readAsDataURL(b)}else b instanceof Object&&b.hasOwnProperty("path")&&h(b.path);return e.promise()},k=function(b,c){var d=b.width,e=b.height;d=d>0?d:a(b).width(),e=e>0?e:a(b).height(),(d>c||e>c)&&(d>e?(e=parseInt(c/d*e),d=c):(d=parseInt(c/e*d),e=c));var f;try{var g=document.createElement("canvas");g.width=d,g.height=e;var h=g.getContext("2d");h.drawImage(b,0,0,b.width,b.height,0,0,d,e),f=g.toDataURL()}catch(i){f=null}return f?(/^data\:image\/(png|jpe?g|gif);base64,[0-9A-Za-z\+\/\=]+$/.test(f)||(f=null),f?{src:f,w:d,h:e}:null):null},l=function(a,b){var c=o.call(this,a,b);return-1===c?(this.reset_input(),!1):c&&0!=c.length?((c instanceof Array||d&&c instanceof FileList)&&(a=c),c=!0,this.settings.before_change&&(c=this.settings.before_change.call(this.element,a,b)),-1===c?(this.reset_input(),!1):c&&0!=c.length?((c instanceof Array||d&&c instanceof FileList)&&(a=c),a):(this.$element.data("ace_input_files")||this.reset_input(),!1)):(this.$element.data("ace_input_files")||this.reset_input(),!1)},m=function(a){return a?("string"==typeof a&&(a=[a]),0==a.length?null:new RegExp(".(?:"+a.join("|")+")$","i")):null},n=function(a){return a?("string"==typeof a&&(a=[a]),0==a.length?null:new RegExp("^(?:"+a.join("|").replace(/\//g,"\\/")+")$","i")):null},o=function(b,c){var d=m(this.settings.allowExt),e=m(this.settings.denyExt),g=n(this.settings.allowMime),h=n(this.settings.denyMime),i=this.settings.maxSize||!1;if(!(d||e||g||h||i))return!0;for(var j=[],k={},l=0;l<b.length;l++){var o=b[l],p=f?o.name:o;if(!d||d.test(p))if(e&&e.test(p))"ext"in k||(k.ext=[]),k.ext.push(p);else{var q;if(f){if((q=a.trim(o.type)).length>0){if(g&&!g.test(q)){"mime"in k||(k.mime=[]),k.mime.push(p);continue}if(h&&h.test(q)){"mime"in k||(k.mime=[]),k.mime.push(p);continue}}i&&o.size>i?("size"in k||(k.size=[]),k.size.push(p)):j.push(o)}else j.push(o)}else"ext"in k||(k.ext=[]),k.ext.push(p)}if(j.length==b.length)return b;var r={ext:0,mime:0,size:0};"ext"in k&&(r.ext=k.ext.length),"mime"in k&&(r.mime=k.mime.length),"size"in k&&(r.size=k.size.length);var s;return this.$element.trigger(s=new a.Event("file.error.ace"),{file_count:b.length,invalid_count:b.length-j.length,error_list:k,error_count:r,dropped:c}),s.isDefaultPrevented()?-1:j};a.fn.aceFileInput=a.fn.ace_file_input=function(c,d){var e,f=this.each(function(){var b=a(this),f=b.data("ace_file_input"),h="object"==typeof c&&c;f||b.data("ace_file_input",f=new g(this,h)),"string"==typeof c&&(e=f[c](d))});return e===b?f:e},a.fn.ace_file_input.defaults={style:!1,no_file:"No File ...",no_icon:"fa fa-upload",btn_choose:"Choose",btn_change:"Change",icon_remove:"fa fa-times",droppable:!1,thumbnail:!1,allowExt:null,denyExt:null,allowMime:null,denyMime:null,maxSize:!1,before_change:null,before_remove:null,preview_error:null}}(window.jQuery),!function(a){"use strict";var b=function(b,c){this.$element=a(b),this.options=a.extend({},a.fn.bs_typeahead.defaults,c),this.matcher=this.options.matcher||this.matcher,this.sorter=this.options.sorter||this.sorter,this.highlighter=this.options.highlighter||this.highlighter,this.updater=this.options.updater||this.updater,this.source=this.options.source,this.$menu=a(this.options.menu),this.shown=!1,this.listen()};b.prototype={constructor:b,select:function(){var a=this.$menu.find(".active").attr("data-value");return this.$element.val(this.updater(a)).change(),this.hide()},updater:function(a){return a},show:function(){var b=a.extend({},this.$element.position(),{height:this.$element[0].offsetHeight});return this.$menu.insertAfter(this.$element).css({top:b.top+b.height,left:b.left}).show(),this.shown=!0,this},hide:function(){return this.$menu.hide(),this.shown=!1,this},lookup:function(){var b;return this.query=this.$element.val(),!this.query||this.query.length<this.options.minLength?this.shown?this.hide():this:(b=a.isFunction(this.source)?this.source(this.query,a.proxy(this.process,this)):this.source,b?this.process(b):this)},process:function(b){var c=this;return b=a.grep(b,function(a){return c.matcher(a)}),b=this.sorter(b),b.length?this.render(b.slice(0,this.options.items)).show():this.shown?this.hide():this},matcher:function(a){return~a.toLowerCase().indexOf(this.query.toLowerCase())},sorter:function(a){for(var b,c=[],d=[],e=[];b=a.shift();)b.toLowerCase().indexOf(this.query.toLowerCase())?~b.indexOf(this.query)?d.push(b):e.push(b):c.push(b);return c.concat(d,e)},highlighter:function(a){var b=this.query.replace(/[\-\[\]{}()*+?.,\\\^$|#\s]/g,"\\$&");return a.replace(new RegExp("("+b+")","ig"),function(a,b){return"<strong>"+b+"</strong>"})},render:function(b){var c=this;return b=a(b).map(function(b,d){return b=a(c.options.item).attr("data-value",d),b.find("a").html(c.highlighter(d)),b[0]}),b.first().addClass("active"),this.$menu.html(b),this},next:function(){var b=this.$menu.find(".active").removeClass("active"),c=b.next();c.length||(c=a(this.$menu.find("li")[0])),c.addClass("active")},prev:function(){var a=this.$menu.find(".active").removeClass("active"),b=a.prev();b.length||(b=this.$menu.find("li").last()),b.addClass("active")},listen:function(){this.$element.on("focus",a.proxy(this.focus,this)).on("blur",a.proxy(this.blur,this)).on("keypress",a.proxy(this.keypress,this)).on("keyup",a.proxy(this.keyup,this)),this.eventSupported("keydown")&&this.$element.on("keydown",a.proxy(this.keydown,this)),this.$menu.on("click",a.proxy(this.click,this)).on("mouseenter","li",a.proxy(this.mouseenter,this)).on("mouseleave","li",a.proxy(this.mouseleave,this))},eventSupported:function(a){var b=a in this.$element;return b||(this.$element.setAttribute(a,"return;"),b="function"==typeof this.$element[a]),b},move:function(a){if(this.shown){switch(a.keyCode){case 9:case 13:case 27:a.preventDefault();break;case 38:a.preventDefault(),this.prev();break;case 40:a.preventDefault(),this.next()}a.stopPropagation()}},keydown:function(b){this.suppressKeyPressRepeat=~a.inArray(b.keyCode,[40,38,9,13,27]),this.move(b)},keypress:function(a){this.suppressKeyPressRepeat||this.move(a)},keyup:function(a){switch(a.keyCode){case 40:case 38:case 16:case 17:case 18:break;case 9:case 13:if(!this.shown)return;this.select();break;case 27:if(!this.shown)return;this.hide();break;default:this.lookup()}a.stopPropagation(),a.preventDefault()},focus:function(){this.focused=!0},blur:function(){this.focused=!1,!this.mousedover&&this.shown&&this.hide()},click:function(a){a.stopPropagation(),a.preventDefault(),this.select(),this.$element.focus()},mouseenter:function(b){this.mousedover=!0,this.$menu.find(".active").removeClass("active"),a(b.currentTarget).addClass("active")},mouseleave:function(){this.mousedover=!1,!this.focused&&this.shown&&this.hide()}};var c=a.fn.bs_typeahead;a.fn.bs_typeahead=function(c){return this.each(function(){var d=a(this),e=d.data("bs_typeahead"),f="object"==typeof c&&c;e||d.data("bs_typeahead",e=new b(this,f)),"string"==typeof c&&e[c]()})},a.fn.bs_typeahead.defaults={source:[],items:8,menu:'<ul class="typeahead dropdown-menu"></ul>',item:'<li><a href="#"></a></li>',minLength:1},a.fn.bs_typeahead.Constructor=b,a.fn.bs_typeahead.noConflict=function(){return a.fn.bs_typeahead=c,this},a(document).on("focus.bs_typeahead.data-api",'[data-provide="bs_typeahead"]',function(){var b=a(this);b.data("bs_typeahead")||b.bs_typeahead(b.data())})}(window.jQuery),function(a){a.fn.ace_wysiwyg=function(b){var c=a.extend({speech_button:!0,wysiwyg:{}},b),d=["#ac725e","#d06b64","#f83a22","#fa573c","#ff7537","#ffad46","#42d692","#16a765","#7bd148","#b3dc6c","#fbe983","#fad165","#92e1c0","#9fe1e7","#9fc6e7","#4986e7","#9a9cff","#b99aff","#c2c2c2","#cabdbf","#cca6ac","#f691b2","#cd74e6","#a47ae2","#444444"],e={font:{values:["Arial","Courier","Comic Sans MS","Helvetica","Open Sans","Tahoma","Verdana"],icon:"fa fa-font",title:"Font"},fontSize:{values:{5:"Huge",3:"Normal",1:"Small"},icon:"fa fa-text-height",title:"Font Size"},bold:{icon:"fa fa-bold",title:"Bold (Ctrl/Cmd+B)"},italic:{icon:"fa fa-italic",title:"Italic (Ctrl/Cmd+I)"},strikethrough:{icon:"fa fa-strikethrough",title:"Strikethrough"},underline:{icon:"fa fa-underline",title:"Underline"},insertunorderedlist:{icon:"fa fa-list-ul",title:"Bullet list"},insertorderedlist:{icon:"fa fa-list-ol",title:"Number list"},outdent:{icon:"fa fa-outdent",title:"Reduce indent (Shift+Tab)"},indent:{icon:"fa fa-indent",title:"Indent (Tab)"},justifyleft:{icon:"fa fa-align-left",title:"Align Left (Ctrl/Cmd+L)"},justifycenter:{icon:"fa fa-align-center",title:"Center (Ctrl/Cmd+E)"},justifyright:{icon:"fa fa-align-right",title:"Align Right (Ctrl/Cmd+R)"},justifyfull:{icon:"fa fa-align-justify",title:"Justify (Ctrl/Cmd+J)"},createLink:{icon:"fa fa-link",title:"Hyperlink",button_text:"Add",placeholder:"URL",button_class:"btn-primary"},unlink:{icon:"fa fa-chain-broken",title:"Remove Hyperlink"},insertImage:{icon:"fa fa-picture-o",title:"Insert picture",button_text:'<i class="'+ace.vars.icon+'fa fa-file"></i> Choose Image &hellip;',placeholder:"Image URL",button_insert:"Insert",button_class:"btn-success",button_insert_class:"btn-primary",choose_file:!0},foreColor:{values:d,title:"Change Color"},backColor:{values:d,title:"Change Background Color"},undo:{icon:"fa fa-undo",title:"Undo (Ctrl/Cmd+Z)"},redo:{icon:"fa fa-repeat",title:"Redo (Ctrl/Cmd+Y)"},viewSource:{icon:"fa fa-code",title:"View Source"}},f=c.toolbar||["font",null,"fontSize",null,"bold","italic","strikethrough","underline",null,"insertunorderedlist","insertorderedlist","outdent","indent",null,"justifyleft","justifycenter","justifyright","justifyfull",null,"createLink","unlink",null,"insertImage",null,"foreColor",null,"undo","redo",null,"viewSource"];return this.each(function(){var b=' <div class="wysiwyg-toolbar btn-toolbar center"> <div class="btn-group"> ';for(var d in f)if(f.hasOwnProperty(d)){var g=f[d];if(null===g){b+=' </div> <div class="btn-group"> ';continue}if("string"==typeof g&&g in e)g=e[g],g.name=f[d];else{if(!("object"==typeof g&&g.name in e))continue;g=a.extend(e[g.name],g)}var h="className"in g?g.className:"btn-default";switch(g.name){case"font":b+=' <a class="btn btn-sm '+h+' dropdown-toggle" data-toggle="dropdown" title="'+g.title+'"><i class="'+ace.vars.icon+g.icon+'"></i><i class="'+ace.vars.icon+'fa fa-angle-down icon-on-right"></i></a> ',b+=' <ul class="dropdown-menu dropdown-light dropdown-caret">';for(var i in g.values)g.values.hasOwnProperty(i)&&(b+=' <li><a data-edit="fontName '+g.values[i]+'" style="font-family:\''+g.values[i]+"'\">"+g.values[i]+"</a></li> ");b+=" </ul>";break;case"fontSize":b+=' <a class="btn btn-sm '+h+' dropdown-toggle" data-toggle="dropdown" title="'+g.title+'"><i class="'+ace.vars.icon+g.icon+'"></i>&nbsp;<i class="'+ace.vars.icon+'fa fa-angle-down icon-on-right"></i></a> ',b+=' <ul class="dropdown-menu dropdown-light dropdown-caret"> ';for(var j in g.values)g.values.hasOwnProperty(j)&&(b+=' <li><a data-edit="fontSize '+j+'"><font size="'+j+'">'+g.values[j]+"</font></a></li> ");b+=" </ul> ";break;case"createLink":b+=' <div class="btn-group"> <a class="btn btn-sm '+h+' dropdown-toggle" data-toggle="dropdown" title="'+g.title+'"><i class="'+ace.vars.icon+g.icon+'"></i></a> ',b+=' <div class="dropdown-menu dropdown-caret dropdown-menu-right">							 <div class="input-group">								<input class="form-control" placeholder="'+g.placeholder+'" type="text" data-edit="'+g.name+'" />								<span class="input-group-btn">									<button class="btn btn-sm '+g.button_class+'" type="button">'+g.button_text+"</button>								</span>							 </div>						</div> </div>";break;case"insertImage":b+=' <div class="btn-group"> <a class="btn btn-sm '+h+' dropdown-toggle" data-toggle="dropdown" title="'+g.title+'"><i class="'+ace.vars.icon+g.icon+'"></i></a> ',b+=' <div class="dropdown-menu dropdown-caret dropdown-menu-right">							 <div class="input-group">								<input class="form-control" placeholder="'+g.placeholder+'" type="text" data-edit="'+g.name+'" />								<span class="input-group-btn">									<button class="btn btn-sm '+g.button_insert_class+'" type="button">'+g.button_insert+"</button>								</span>							 </div>",g.choose_file&&"FileReader"in window&&(b+='<div class="space-2"></div>							 <label class="center block no-margin-bottom">								<button class="btn btn-sm '+g.button_class+' wysiwyg-choose-file" type="button">'+g.button_text+'</button>								<input type="file" data-edit="'+g.name+'" />							  </label>'),b+=" </div> </div>";break;case"foreColor":case"backColor":b+=' <select class="hide wysiwyg_colorpicker" title="'+g.title+'"> ',a.each(g.values,function(a,c){b+=' <option value="'+c+'">'+c+"</option> "}),b+=" </select> ",b+=' <input style="display:none;" disabled class="hide" type="text" data-edit="'+g.name+'" /> ';break;case"viewSource":b+=' <a class="btn btn-sm '+h+'" data-view="source" title="'+g.title+'"><i class="'+ace.vars.icon+g.icon+'"></i></a> ';break;default:b+=' <a class="btn btn-sm '+h+'" data-edit="'+g.name+'" title="'+g.title+'"><i class="'+ace.vars.icon+g.icon+'"></i></a> '}}b+=" </div> ";var k;c.speech_button&&"onwebkitspeechchange"in(k=document.createElement("input"))&&(b+=' <input class="wysiwyg-speech-input" type="text" data-edit="inserttext" x-webkit-speech />'),k=null,b+=" </div> ",b=c.toolbar_place?c.toolbar_place.call(this,b):a(this).before(b).prev(),b.find("a[title]").tooltip({animation:!1,container:"body"}),b.find(".dropdown-menu input[type=text]").on("click",function(){return!1}).on("change",function(){a(this).closest(".dropdown-menu").siblings(".dropdown-toggle").dropdown("toggle")}).on("keydown",function(b){27==b.which?(this.value="",a(this).change()):13==b.which&&(b.preventDefault(),b.stopPropagation(),a(this).change())}),b.find("input[type=file]").prev().on(ace.click_event,function(){a(this).next().click()}),b.find(".wysiwyg_colorpicker").each(function(){a(this).ace_colorpicker({pull_right:!0}).change(function(){a(this).nextAll("input").eq(0).val(this.value).change()}).next().find(".btn-colorpicker").tooltip({title:this.title,animation:!1,container:"body"})});var l=a(this),m=!1;b.find("a[data-view=source]").on("click",function(b){if(b.preventDefault(),m){var c=l.next();l.html(c.val()).show(),c.remove(),a(this).removeClass("active")}else a("<textarea />").css({width:l.outerWidth(),height:l.outerHeight()}).val(l.html()).insertAfter(l),l.hide(),a(this).addClass("active");m=!m});var n=a.extend({},{activeToolbarClass:"active",toolbarSelector:b},c.wysiwyg||{});a(this).wysiwyg(n)}),this}}(window.jQuery),function(a,b){function c(b,c){var d=ace.helper.getAttrSettings(b,a.fn.ace_spinner.defaults),e=a.extend({},a.fn.ace_spinner.defaults,c,d),f=e.max;f=(""+f).length;var g=parseInt(Math.max(20*f+40,90)),h=a(b),i="btn-sm",j=2;h.hasClass("input-sm")?(i="btn-xs",j=1):h.hasClass("input-lg")&&(i="btn-lg",j=3),2==j?g+=25:3==j&&(g+=50),h.addClass("spinbox-input form-control text-center").wrap('<div class="ace-spinner middle">');var k=h.closest(".ace-spinner").spinbox(e).wrapInner("<div class='input-group'></div>"),l=k.data("fu.spinbox");e.on_sides?(h.before('<div class="spinbox-buttons input-group-btn">					<button type="button" class="btn spinbox-down '+i+" "+e.btn_down_class+'">						<i class="icon-only '+ace.vars.icon+e.icon_down+'"></i>					</button>				</div>').after('<div class="spinbox-buttons input-group-btn">					<button type="button" class="btn spinbox-up '+i+" "+e.btn_up_class+'">						<i class="icon-only '+ace.vars.icon+e.icon_up+'"></i>					</button>				</div>'),k.addClass("touch-spinner"),k.css("width",g+"px")):(h.after('<div class="spinbox-buttons input-group-btn">					<button type="button" class="btn spinbox-up '+i+" "+e.btn_up_class+'">						<i class="icon-only '+ace.vars.icon+e.icon_up+'"></i>					</button>					<button type="button" class="btn spinbox-down '+i+" "+e.btn_down_class+'">						<i class="icon-only '+ace.vars.icon+e.icon_down+'"></i>					</button>				</div>'),ace.vars.touch||e.touch_spinner?(k.addClass("touch-spinner"),k.css("width",g+"px")):(h.next().addClass("btn-group-vertical"),k.css("width",g+"px"))),k.on("changed",function(){h.trigger("change")
}),this._call=function(a,b){l[a](b)}}a.fn.ace_spinner=function(d,e){var f,g=this.each(function(){var b=a(this),g=b.data("ace_spinner"),h="object"==typeof d&&d;g||(h=a.extend({},a.fn.ace_spinner.defaults,d),b.data("ace_spinner",g=new c(this,h))),"string"==typeof d&&(f=g._call(d,e))});return f===b?g:f},a.fn.ace_spinner.defaults={icon_up:"fa fa-chevron-up",icon_down:"fa fa-chevron-down",on_sides:!1,btn_up_class:"",btn_down_class:"",max:999,touch_spinner:!1}}(window.jQuery),function(a){a.fn.aceTree=a.fn.ace_tree=function(b){var c={"open-icon":ace.vars.icon+"fa fa-folder-open","close-icon":ace.vars.icon+"fa fa-folder",selectable:!0,"selected-icon":ace.vars.icon+"fa fa-check","unselected-icon":ace.vars.icon+"fa fa-times",loadingHTML:"Loading..."};return this.each(function(){var d=ace.helper.getAttrSettings(this,c),e=a.extend({},c,b,d),f=a(this);f.addClass("tree").attr("role","tree"),f.html('<li class="tree-branch hide" data-template="treebranch" role="treeitem" aria-expanded="false">				<div class="tree-branch-header">					<span class="tree-branch-name">						<i class="icon-folder '+e["close-icon"]+'"></i>						<span class="tree-label"></span>					</span>				</div>				<ul class="tree-branch-children" role="group"></ul>				<div class="tree-loader" role="alert">'+e.loadingHTML+'</div>			</div>			<li class="tree-item hide" data-template="treeitem" role="treeitem">				<span class="tree-item-name">				  '+(null==e["unselected-icon"]?"":'<i class="icon-item '+e["unselected-icon"]+'"></i>')+'				  <span class="tree-label"></span>				</span>			</li>'),f.addClass(1==e.selectable?"tree-selectable":"tree-unselectable"),f.tree(e)}),this}}(window.jQuery),function(a){a.fn.aceWizard=a.fn.ace_wizard=function(b){return this.each(function(){var c=a(this);c.wizard(),ace.vars.old_ie&&c.find("ul.steps > li").last().addClass("last-child");var d=b&&b.buttons?a(b.buttons):c.siblings(".wizard-actions").eq(0),e=c.data("fu.wizard");e.$prevBtn.remove(),e.$nextBtn.remove(),e.$prevBtn=d.find(".btn-prev").eq(0).on(ace.click_event,function(){e.previous()}).attr("disabled","disabled"),e.$nextBtn=d.find(".btn-next").eq(0).on(ace.click_event,function(){e.next()}).removeAttr("disabled"),e.nextText=e.$nextBtn.text();var f=b&&(b.selectedItem&&b.selectedItem.step||b.step);f&&(e.currentStep=f,e.setState())}),this}}(window.jQuery),function(a,b){function c(b,c){var e=this,f=a(b),g="right",h=!1,i=f.hasClass("fade"),j=ace.helper.getAttrSettings(b,a.fn.ace_aside.defaults);if(this.settings=a.extend({},a.fn.ace_aside.defaults,c,j),!this.settings.background||c.scroll_style||j.scroll_style||(this.settings.scroll_style="scroll-white no-track"),this.container=this.settings.container,this.container)try{a(this.container).get(0)==document.body&&(this.container=null)}catch(k){}this.container&&(this.settings.backdrop=!1,f.addClass("aside-contained"));var l=f.find(".modal-dialog"),m=f.find(".modal-content"),n=300;this.initiate=function(){b.className=b.className.replace(/(\s|^)aside\-(right|top|left|bottom)(\s|$)/gi,"$1$3"),g=this.settings.placement,g&&(g=a.trim(g.toLowerCase())),g&&/right|top|left|bottom/.test(g)||(g="right"),f.attr("data-placement",g),f.addClass("aside-"+g),/right|left/.test(g)?(h=!0,f.addClass("aside-vc")):f.addClass("aside-hz"),this.settings.fixed&&f.addClass("aside-fixed"),this.settings.background&&f.addClass("aside-dark"),this.settings.offset&&f.addClass("navbar-offset"),this.settings.transition||f.addClass("transition-off"),f.addClass("aside-hidden"),this.insideContainer(),l=f.find(".modal-dialog"),m=f.find(".modal-content"),this.settings.body_scroll||f.on("mousewheel.aside DOMMouseScroll.aside touchmove.aside pointermove.aside",function(b){return a.contains(m[0],b.target)?void 0:(b.preventDefault(),!1)}),0==this.settings.backdrop&&f.addClass("no-backdrop")},this.show=function(){if(0==this.settings.backdrop)try{f.data("bs.modal").$backdrop.remove()}catch(b){}this.container?a(this.container).addClass("overflow-hidden"):f.css("position","fixed"),f.removeClass("aside-hidden")},this.hide=function(){this.container&&(this.container.addClass("overflow-hidden"),ace.vars.firefox&&b.offsetHeight),o(),ace.vars.transition&&!i&&f.one("bsTransitionEnd",function(){f.addClass("aside-hidden"),f.css("position",""),e.container&&e.container.removeClass("overflow-hidden")}).emulateTransitionEnd(n)},this.shown=function(){if(o(),a("body").removeClass("modal-open").css("padding-right",""),"invisible"==this.settings.backdrop)try{f.data("bs.modal").$backdrop.css("opacity",0)}catch(b){}var c=h?m.height():l.height();ace.vars.touch?m.addClass("overflow-scroll").css("max-height",c+"px"):m.hasClass("ace-scroll")||m.ace_scroll({size:c,reset:!0,mouseWheelLock:!0,lockAnyway:!this.settings.body_scroll,styleClass:this.settings.scroll_style,observeContent:!0,hideOnIdle:!ace.vars.old_ie,hideDelay:1500}),d.off("resize.modal.aside").on("resize.modal.aside",function(){if(ace.vars.touch)m.css("max-height",(h?m.height():l.height())+"px");else{m.ace_scroll("disable");var a=h?m.height():l.height();m.ace_scroll("update",{size:a}).ace_scroll("enable").ace_scroll("reset")}}).triggerHandler("resize.modal.aside"),e.container&&ace.vars.transition&&!i&&f.one("bsTransitionEnd",function(){e.container.removeClass("overflow-hidden")}).emulateTransitionEnd(n)},this.hidden=function(){d.off(".aside"),(!ace.vars.transition||i)&&(f.addClass("aside-hidden"),f.css("position",""))},this.insideContainer=function(){var b=a(".main-container"),c=f.find(".modal-dialog");if(c.css({right:"",left:""}),b.hasClass("container")){var e=!1;1==h&&(c.css(g,parseInt((d.width()-b.width())/2)),e=!0),e&&ace.vars.firefox&&ace.helper.redraw(b[0])}},this.flip=function(){var a={right:"left",left:"right",top:"bottom",bottom:"top"};f.removeClass("aside-"+g).addClass("aside-"+a[g]),g=a[g]};var o=function(){var a=f.find(".aside-trigger");if(0!=a.length){a.toggleClass("open");var b=a.find(ace.vars[".icon"]);0!=b.length&&b.toggleClass(b.attr("data-icon1")+" "+b.attr("data-icon2"))}};this.initiate(),this.container&&(this.container=a(this.container)),f.appendTo(this.container||"body")}var d=a(window);a(document).on("show.bs.modal",".modal.aside",function(){a(".aside.in").modal("hide"),a(this).ace_aside("show")}).on("hide.bs.modal",".modal.aside",function(){a(this).ace_aside("hide")}).on("shown.bs.modal",".modal.aside",function(){a(this).ace_aside("shown")}).on("hidden.bs.modal",".modal.aside",function(){a(this).ace_aside("hidden")}),a(window).on("resize.aside_container",function(){a(".modal.aside").ace_aside("insideContainer")}),a(document).on("settings.ace.aside",function(b,c){"main_container_fixed"==c&&a(".modal.aside").ace_aside("insideContainer")}),a.fn.aceAside=a.fn.ace_aside=function(d,e){var f,g=this.each(function(){var b=a(this),g=b.data("ace_aside"),h="object"==typeof d&&d;g||b.data("ace_aside",g=new c(this,h)),"string"==typeof d&&"function"==typeof g[d]&&(f=e instanceof Array?g[d].apply(g,e):g[d](e))});return f===b?g:f},a.fn.ace_aside.defaults={fixed:!1,background:!1,offset:!1,body_scroll:!1,transition:!0,scroll_style:"scroll-dark no-track",container:null,backdrop:!1,placement:"right"}}(window.jQuery);"ace"in window||(window.ace={}),ace.config={cookie_expiry:604800,cookie_path:"",storage_method:2},"vars"in window.ace||(window.ace.vars={}),ace.vars.very_old_ie=!("querySelector"in document.documentElement),ace.settings={is:function(a,b){return 1==ace.data.get("settings",a+"-"+b)},exists:function(a,b){return null!==ace.data.get("settings",a+"-"+b)},set:function(a,b){ace.data.set("settings",a+"-"+b,1)},unset:function(a,b){ace.data.set("settings",a+"-"+b,-1)},remove:function(a,b){ace.data.remove("settings",a+"-"+b)},navbar_fixed:function(a,b,c,d){if(ace.vars.very_old_ie)return!1;var a=a||"#navbar";if("string"==typeof a&&(a=document.querySelector(a)),!a)return!1;if(b=b||!1,c=c&&!0,!b&&d!==!1){var e=null;(ace.settings.is("sidebar","fixed")||(e=document.getElementById("sidebar"))&&ace.hasClass(e,"sidebar-fixed"))&&ace.settings.sidebar_fixed(e,!1,c)}b?(ace.hasClass(a,"navbar-fixed-top")||ace.addClass(a,"navbar-fixed-top"),c!==!1&&ace.settings.set("navbar","fixed")):(ace.removeClass(a,"navbar-fixed-top"),c!==!1&&ace.settings.unset("navbar","fixed"));try{document.getElementById("ace-settings-navbar").checked=b}catch(f){}window.jQuery&&jQuery(document).trigger("settings.ace",["navbar_fixed",b,a])},sidebar_fixed:function(a,b,c,d){if(ace.vars.very_old_ie)return!1;var a=a||"#sidebar";if("string"==typeof a&&(a=document.querySelector(a)),!a)return!1;if(b=b||!1,c=c&&!0,!b&&d!==!1){var e=null;(ace.settings.is("breadcrumbs","fixed")||(e=document.getElementById("breadcrumbs"))&&ace.hasClass(e,"breadcrumbs-fixed"))&&ace.settings.breadcrumbs_fixed(e,!1,c)}if(b&&d!==!1&&!ace.settings.is("navbar","fixed")&&ace.settings.navbar_fixed(null,!0,c),b){if(!ace.hasClass(a,"sidebar-fixed")){ace.addClass(a,"sidebar-fixed");var f=document.getElementById("menu-toggler");f&&ace.addClass(f,"fixed")}c!==!1&&ace.settings.set("sidebar","fixed")}else{ace.removeClass(a,"sidebar-fixed");var f=document.getElementById("menu-toggler");f&&ace.removeClass(f,"fixed"),c!==!1&&ace.settings.unset("sidebar","fixed")}try{document.getElementById("ace-settings-sidebar").checked=b}catch(g){}window.jQuery&&jQuery(document).trigger("settings.ace",["sidebar_fixed",b,a])},breadcrumbs_fixed:function(a,b,c,d){if(ace.vars.very_old_ie)return!1;var a=a||"#breadcrumbs";if("string"==typeof a&&(a=document.querySelector(a)),!a)return!1;b=b||!1,c=c&&!0,b&&d!==!1&&!ace.settings.is("sidebar","fixed")&&ace.settings.sidebar_fixed(null,!0,c),b?(ace.hasClass(a,"breadcrumbs-fixed")||ace.addClass(a,"breadcrumbs-fixed"),c!==!1&&ace.settings.set("breadcrumbs","fixed")):(ace.removeClass(a,"breadcrumbs-fixed"),c!==!1&&ace.settings.unset("breadcrumbs","fixed"));try{document.getElementById("ace-settings-breadcrumbs").checked=b}catch(e){}window.jQuery&&jQuery(document).trigger("settings.ace",["breadcrumbs_fixed",b,a])},main_container_fixed:function(a,b,c){if(ace.vars.very_old_ie)return!1;b=b||!1,c=c&&!0;var a=a||"#main-container";if("string"==typeof a&&(a=document.querySelector(a)),!a)return!1;var d=document.getElementById("navbar-container");b?(ace.hasClass(a,"container")||ace.addClass(a,"container"),d&&!ace.hasClass(d,"container")&&ace.addClass(d,"container"),c!==!1&&ace.settings.set("main-container","fixed")):(ace.removeClass(a,"container"),d&&ace.removeClass(d,"container"),c!==!1&&ace.settings.unset("main-container","fixed"));try{document.getElementById("ace-settings-add-container").checked=b}catch(e){}if(navigator.userAgent.match(/webkit/i)){var f=document.getElementById("sidebar");ace.toggleClass(f,"menu-min"),setTimeout(function(){ace.toggleClass(f,"menu-min")},0)}window.jQuery&&jQuery(document).trigger("settings.ace",["main_container_fixed",b,a])},sidebar_collapsed:function(a,b,c){if(ace.vars.very_old_ie)return!1;var a=a||"#sidebar";if("string"==typeof a&&(a=document.querySelector(a)),!a)return!1;if(b=b||!1,b?(ace.addClass(a,"menu-min"),c!==!1&&ace.settings.set("sidebar","collapsed")):(ace.removeClass(a,"menu-min"),c!==!1&&ace.settings.unset("sidebar","collapsed")),window.jQuery&&jQuery(document).trigger("settings.ace",["sidebar_collapsed",b,a]),!window.jQuery){var d=document.querySelector('.sidebar-collapse[data-target="#'+(a.getAttribute("id")||"")+'"]');if(d||(d=a.querySelector(".sidebar-collapse")),!d)return;var e,f,g=d.querySelector("[data-icon1][data-icon2]");if(!g)return;e=g.getAttribute("data-icon1"),f=g.getAttribute("data-icon2"),b?(ace.removeClass(g,e),ace.addClass(g,f)):(ace.removeClass(g,f),ace.addClass(g,e))}}},ace.settings.check=function(a,b){if(ace.settings.exists(a,b)){var c=ace.settings.is(a,b),d={"navbar-fixed":"navbar-fixed-top","sidebar-fixed":"sidebar-fixed","breadcrumbs-fixed":"breadcrumbs-fixed","sidebar-collapsed":"menu-min","main-container-fixed":"container"},e=document.getElementById(a);c!=ace.hasClass(e,d[a+"-"+b])&&ace.settings[a.replace("-","_")+"_"+b](null,c)}},ace.data_storage=function(a,b){var c="ace_",d=null,e=0;(1==a||a===b)&&"localStorage"in window&&null!==window.localStorage?(d=ace.storage,e=1):null==d&&(2==a||a===b)&&"cookie"in document&&null!==document.cookie&&(d=ace.cookie,e=2),this.set=function(a,b,f,g,h){if(d)if(f===h)f=b,b=a,null==f?d.remove(c+b):1==e?d.set(c+b,f):2==e&&d.set(c+b,f,ace.config.cookie_expiry,g||ace.config.cookie_path);else if(1==e)null==f?d.remove(c+a+"_"+b):d.set(c+a+"_"+b,f);else if(2==e){var i=d.get(c+a),j=i?JSON.parse(i):{};if(null==f){if(delete j[b],0==ace.sizeof(j))return void d.remove(c+a)}else j[b]=f;d.set(c+a,JSON.stringify(j),ace.config.cookie_expiry,g||ace.config.cookie_path)}},this.get=function(a,b,f){if(!d)return null;if(b===f)return b=a,d.get(c+b);if(1==e)return d.get(c+a+"_"+b);if(2==e){var g=d.get(c+a),h=g?JSON.parse(g):{};return b in h?h[b]:null}},this.remove=function(a,b,c){d&&(b===c?(b=a,this.set(b,null)):this.set(a,b,null))}},ace.cookie={get:function(a){var b,c,d=document.cookie,e=a+"=";if(d){if(c=d.indexOf("; "+e),-1==c){if(c=d.indexOf(e),0!=c)return null}else c+=2;return b=d.indexOf(";",c),-1==b&&(b=d.length),decodeURIComponent(d.substring(c+e.length,b))}},set:function(a,b,c,d,e,f){var g=new Date;"object"==typeof c&&c.toGMTString?c=c.toGMTString():parseInt(c,10)?(g.setTime(g.getTime()+1e3*parseInt(c,10)),c=g.toGMTString()):c="",document.cookie=a+"="+encodeURIComponent(b)+(c?"; expires="+c:"")+(d?"; path="+d:"")+(e?"; domain="+e:"")+(f?"; secure":"")},remove:function(a,b){this.set(a,"",-1e3,b)}},ace.storage={get:function(a){return window.localStorage.getItem(a)},set:function(a,b){window.localStorage.setItem(a,b)},remove:function(a){window.localStorage.removeItem(a)}},ace.sizeof=function(a){var b=0;for(var c in a)a.hasOwnProperty(c)&&b++;return b},ace.hasClass=function(a,b){return(" "+a.className+" ").indexOf(" "+b+" ")>-1},ace.addClass=function(a,b){if(!ace.hasClass(a,b)){var c=a.className;a.className=c+(c.length?" ":"")+b}},ace.removeClass=function(a,b){ace.replaceClass(a,b)},ace.replaceClass=function(a,b,c){var d=new RegExp("(^|\\s)"+b+"(\\s|$)","i");a.className=a.className.replace(d,function(a,b,d){return c?b+c+d:" "}).replace(/^\s+|\s+$/g,"")},ace.toggleClass=function(a,b){ace.hasClass(a,b)?ace.removeClass(a,b):ace.addClass(a,b)},ace.isHTTMlElement=function(a){return window.HTMLElement?a instanceof HTMLElement:"nodeType"in a?1==a.nodeType:!1},ace.data=new ace.data_storage(ace.config.storage_method);/*!
 * Ace v1.3.3
 */
if("undefined"==typeof jQuery)throw new Error("Ace's JavaScript requires jQuery");!function(){"ace"in window||(window.ace={}),"helper"in window.ace||(window.ace.helper={}),"vars"in window.ace||(window.ace.vars={}),window.ace.vars.icon=" ace-icon ",window.ace.vars[".icon"]=".ace-icon",ace.vars.touch="ontouchstart"in window;var a=navigator.userAgent;ace.vars.webkit=!!a.match(/AppleWebKit/i),ace.vars.safari=!!a.match(/Safari/i)&&!a.match(/Chrome/i),ace.vars.android=ace.vars.safari&&!!a.match(/Android/i),ace.vars.ios_safari=!!a.match(/OS ([4-9])(_\d)+ like Mac OS X/i)&&!a.match(/CriOS/i),ace.vars.ie=window.navigator.msPointerEnabled||document.all&&document.querySelector,ace.vars.old_ie=document.all&&!document.addEventListener,ace.vars.very_old_ie=document.all&&!document.querySelector,ace.vars.firefox="MozAppearance"in document.documentElement.style,ace.vars.non_auto_fixed=ace.vars.android||ace.vars.ios_safari}(),function(a){ace.click_event=ace.vars.touch&&a.fn.tap?"tap":"click"}(jQuery),jQuery(function(a){function b(){ace.vars.non_auto_fixed&&a("body").addClass("mob-safari"),ace.vars.transition=!!a.support.transition.end}function c(){var b=a(".sidebar");a.fn.ace_sidebar&&b.ace_sidebar(),a.fn.ace_sidebar_scroll&&b.ace_sidebar_scroll({include_toggle:!1||ace.vars.safari||ace.vars.ios_safari}),a.fn.ace_sidebar_hover&&b.ace_sidebar_hover({sub_hover_delay:750,sub_scroll_style:"no-track scroll-thin scroll-margin scroll-visible"})}function d(){if(a.fn.ace_ajax){window.Pace&&(window.paceOptions={ajax:!0,document:!0,eventLag:!1});var b={close_active:!0,default_url:"page/index",content_url:function(a){if(!a.match(/^page\//))return!1;var b=document.location.pathname;return b.match(/(\/ajax\/)(index\.html)?/)?b.replace(/(\/ajax\/)(index\.html)?/,"/ajax/content/"+a.replace(/^page\//,"")+".html"):b+"?"+a.replace(/\//,"=")}};window.Pace&&(b.loading_overlay="body"),a("[data-ajax-content=true]").ace_ajax(b),a(window).on("error.ace_ajax",function(){a("[data-ajax-content=true]").each(function(){var b=a(this);b.ace_ajax("working")&&(window.Pace&&Pace.running&&Pace.stop(),b.ace_ajax("stopLoading",!0))})})}}function e(){var b=!!a.fn.ace_scroll;b&&a(".dropdown-content").ace_scroll({reset:!1,mouseWheelLock:!0}),b&&!ace.vars.old_ie&&(a(window).on("resize.reset_scroll",function(){a(".ace-scroll:not(.scroll-disabled)").not(":hidden").ace_scroll("reset")}),b&&a(document).on("settings.ace.reset_scroll",function(b,c){"sidebar_collapsed"==c&&a(".ace-scroll:not(.scroll-disabled)").not(":hidden").ace_scroll("reset")}))}function f(){a(document).on("click.dropdown.pos",'.dropdown-toggle[data-position="auto"]',function(){var b=a(this).offset(),c=a(this.parentNode);parseInt(b.top+a(this).height())+50>ace.helper.scrollTop()+ace.helper.winHeight()-c.find(".dropdown-menu").eq(0).height()?c.addClass("dropup"):c.removeClass("dropup")})}function g(){a('.ace-nav [class*="icon-animated-"]').closest("a").one("click",function(){var b=a(this).find('[class*="icon-animated-"]').eq(0),c=b.attr("class").match(/icon\-animated\-([\d\w]+)/);b.removeClass(c[0])}),a(document).on("click",".dropdown-navbar .nav-tabs",function(b){b.stopPropagation();{var c;b.target}(c=a(b.target).closest("[data-toggle=tab]"))&&c.length>0&&(c.tab("show"),b.preventDefault(),a(window).triggerHandler("resize.navbar.dropdown"))})}function h(){a(".sidebar .nav-list .badge[title],.sidebar .nav-list .badge[title]").each(function(){var b=a(this).attr("class").match(/tooltip\-(?:\w+)/);b=b?b[0]:"tooltip-error",a(this).tooltip({placement:function(b,c){var d=a(c).offset();return parseInt(d.left)<parseInt(document.body.scrollWidth/2)?"right":"left"},container:"body",template:'<div class="tooltip '+b+'"><div class="tooltip-arrow"></div><div class="tooltip-inner"></div></div>'})})}function i(){var b=a(".btn-scroll-up");if(b.length>0){var c=!1;a(window).on("scroll.scroll_btn",function(){var a=ace.helper.scrollTop(),d=ace.helper.winHeight(),e=document.body.scrollHeight;a>parseInt(d/4)||a>0&&e>=d&&d+a>=e-1?c||(b.addClass("display"),c=!0):c&&(b.removeClass("display"),c=!1)}).triggerHandler("scroll.scroll_btn"),b.on(ace.click_event,function(){var b=Math.min(500,Math.max(100,parseInt(ace.helper.scrollTop()/3)));return a("html,body").animate({scrollTop:0},b),!1})}}function j(){if(ace.vars.webkit){var b=a(".ace-nav").get(0);b&&a(window).on("resize.webkit_fix",function(){ace.helper.redraw(b)})}ace.vars.ios_safari&&a(document).on("ace.settings.ios_fix",function(b,c,d){"navbar_fixed"==c&&(a(document).off("focus.ios_fix blur.ios_fix","input,textarea,.wysiwyg-editor"),1==d&&a(document).on("focus.ios_fix","input,textarea,.wysiwyg-editor",function(){a(window).on("scroll.ios_fix",function(){var b=a("#navbar").get(0);b&&ace.helper.redraw(b)})}).on("blur.ios_fix","input,textarea,.wysiwyg-editor",function(){a(window).off("scroll.ios_fix")}))}).triggerHandler("ace.settings.ios_fix",["navbar_fixed","fixed"==a("#navbar").css("position")])}function k(){a(document).on("hide.bs.collapse show.bs.collapse",function(b){var c=b.target.getAttribute("id"),d=a('a[href*="#'+c+'"]');0==d.length&&(d=a('a[data-target*="#'+c+'"]')),0!=d.length&&d.find(ace.vars[".icon"]).each(function(){var c,d=a(this),e=null,f=null;return(e=d.attr("data-icon-show"))?f=d.attr("data-icon-hide"):(c=d.attr("class").match(/fa\-(.*)\-(up|down)/))&&(e="fa-"+c[1]+"-down",f="fa-"+c[1]+"-up"),e?("show"==b.type?d.removeClass(e).addClass(f):d.removeClass(f).addClass(e),!1):void 0})})}function l(){function b(){var b=a(this).find("> .dropdown-menu");if("fixed"==b.css("position")){var d=parseInt(a(window).width()),e=d>320?60:d>240?40:30,f=parseInt(d)-e,g=parseInt(a(window).height())-30,h=parseInt(Math.min(f,320));b.css("width",h);var i=!1,j=0,k=b.find(".tab-pane.active .dropdown-content.ace-scroll");0==k.length?k=b.find(".dropdown-content.ace-scroll"):i=!0;var l=k.closest(".dropdown-menu"),m=b[0].scrollHeight;if(1==k.length){var n=k.find(".scroll-content")[0];n&&(m=n.scrollHeight),j+=l.find(".dropdown-header").outerHeight(),j+=l.find(".dropdown-footer").outerHeight();var o=l.closest(".tab-content");0!=o.length&&(j+=o.siblings(".nav-tabs").eq(0).height())}var p=parseInt(Math.min(g,480,m+j)),q=parseInt(Math.abs((f+e-h)/2)),r=parseInt(Math.abs((g+30-p)/2)),s=parseInt(b.css("z-index"))||0;if(b.css({height:p,left:q,right:"auto",top:r-(i?3:1)}),1==k.length&&(ace.vars.touch?k.ace_scroll("disable").css("max-height",p-j).addClass("overflow-scroll"):k.ace_scroll("update",{size:p-j}).ace_scroll("enable").ace_scroll("reset")),b.css("height",p+(i?7:2)),b.hasClass("user-menu")){b.css("height","");var t=a(this).find(".user-info");t.css(1==t.length&&"fixed"==t.css("position")?{left:q,right:"auto",top:r,width:h-2,"max-width":h-2,"z-index":s+1}:{left:"",right:"",top:"",width:"","max-width":"","z-index":""})}a(this).closest(".navbar.navbar-fixed-top").css("z-index",s)}else 0!=b.length&&c.call(this,b);var u=this;a(window).off("resize.navbar.dropdown").one("resize.navbar.dropdown",function(){a(u).triggerHandler("shown.bs.dropdown.navbar")})}function c(b){if(b=b||a(this).find("> .dropdown-menu"),b.length>0&&(b.css({width:"",height:"",left:"",right:"",top:""}).find(".dropdown-content").each(function(){ace.vars.touch&&a(this).css("max-height","").removeClass("overflow-scroll");var b=parseInt(a(this).attr("data-size")||0)||a.fn.ace_scroll.defaults.size;a(this).ace_scroll("update",{size:b}).ace_scroll("enable").ace_scroll("reset")}),b.hasClass("user-menu"))){a(this).find(".user-info").css({left:"",right:"",top:"",width:"","max-width":"","z-index":""})}a(this).closest(".navbar").css("z-index","")}ace.vars.old_ie||a(".ace-nav > li").on("shown.bs.dropdown.navbar",function(){b.call(this)}).on("hidden.bs.dropdown.navbar",function(){a(window).off("resize.navbar.dropdown"),c.call(this)})}b(),c(),d(),e(),f(),g(),h(),i(),j(),k(),l()}),function(a){a.unCamelCase=function(a){return a.replace(/([a-z])([A-Z])/g,function(a,b,c){return b+"-"+c.toLowerCase()})},a.strToVal=function(a){var b=a.match(/^(?:(true)|(false)|(null)|(\-?[\d]+(?:\.[\d]+)?)|(\[.*\]|\{.*\}))$/i),c=a;if(b)if(b[1])c=!0;else if(b[2])c=!1;else if(b[3])c=null;else if(b[4])c=parseFloat(a);else if(b[5])try{c=JSON.parse(a)}catch(d){}return c},a.getAttrSettings=function(b,c,d){var e=c instanceof Array?1:2,d=d?d.replace(/([^\-])$/,"$1-"):"";d="data-"+d;var f={};for(var g in c)if(c.hasOwnProperty(g)){var h,i=1==e?c[g]:g,j=a.unCamelCase(i.replace(/[^A-Za-z0-9]{1,}/g,"-")).toLowerCase();if(!(h=b.getAttribute(d+j)))continue;f[i]=a.strToVal(h)}return f},a.scrollTop=function(){return document.scrollTop||document.documentElement.scrollTop||document.body.scrollTop},a.winHeight=function(){return window.innerHeight||document.documentElement.clientHeight},a.redraw=function(a,b){var c=a.style.display;a.style.display="none",a.offsetHeight,b!==!0?a.style.display=c:setTimeout(function(){a.style.display=c},10)}}(ace.helper),function(a,b){function c(b,c){function e(b){var c="",d=a(".breadcrumb");if(d.length>0&&d.is(":visible")){d.find("> li:not(:first-child)").remove();var e=0;b.parents(".nav li").each(function(){var b=a(this).find("> a"),f=b.clone();f.find("i,.fa,.glyphicon,.ace-icon,.menu-icon,.badge,.label").remove();var g=f.text();f.remove();var h=b.attr("href");if(0==e){var i=a('<li class="active"></li>').appendTo(d);i.text(g),c=g}else{var i=a("<li><a /></li>").insertAfter(d.find("> li:first-child"));i.find("a").attr("href",h).text(g)}e++})}return c}function f(b){var c=g.find(".ajax-append-title");if(c.length>0)document.title=c.text(),c.remove();else if(b.length>0){var d=a.trim(String(document.title).replace(/^(.*)[\-]/,""));d&&(d=" - "+d),b=a.trim(b)+d}}var g=a(b),h=this;g.attr("data-ajax-content","true");var i=ace.helper.getAttrSettings(b,a.fn.ace_ajax.defaults);this.settings=a.extend({},a.fn.ace_ajax.defaults,c,i);var j=!1,k=a();this.force_reload=!1,this.loadUrl=function(a,b){var c=!1;a=a.replace(/^(\#\!)?\#/,""),this.force_reload=b===!1,"function"==typeof this.settings.content_url&&(c=this.settings.content_url(a)),"string"==typeof c&&this.getUrl(c,a,!1)},this.loadAddr=function(a,b,c){this.force_reload=c===!1,this.getUrl(a,b,!1)},this.getUrl=function(b,c,d){if(!j){var i;g.trigger(i=a.Event("ajaxloadstart"),{url:b,hash:c}),i.isDefaultPrevented()||(h.startLoading(),a.ajax({url:b,cache:!this.force_reload}).error(function(){g.trigger("ajaxloaderror",{url:b,hash:c}),h.stopLoading(!0)}).done(function(i){g.trigger("ajaxloaddone",{url:b,hash:c});var j=null,l="";if("function"==typeof h.settings.update_active)j=h.settings.update_active.call(null,c,b);else if(h.settings.update_active===!0&&c&&(j=a('a[data-url="'+c+'"]'),j.length>0)){var m=j.closest(".nav");if(m.length>0){m.find(".active").each(function(){var b="active";(a(this).hasClass("hover")||h.settings.close_active)&&(b+=" open"),a(this).removeClass(b),h.settings.close_active&&a(this).find(" > .submenu").css("display","")});{j.closest("li").addClass("active").parents(".nav li").addClass("active open")}m.closest(".sidebar[data-sidebar-scroll=true]").each(function(){var b=a(this);b.ace_sidebar_scroll("reset"),d&&b.ace_sidebar_scroll("scroll_to_active")})}}"function"==typeof h.settings.update_breadcrumbs?l=h.settings.update_breadcrumbs.call(null,c,b,j):h.settings.update_breadcrumbs===!0&&null!=j&&j.length>0&&(l=e(j)),i=String(i).replace(/<(title|link)([\s\>])/gi,'<div class="hidden ajax-append-$1"$2').replace(/<\/(title|link)\>/gi,"</div>"),k.addClass("content-loaded").detach(),g.empty().html(i),a(h.settings.loading_overlay||g).append(k),setTimeout(function(){a("head").find("link.ace-ajax-stylesheet").remove();for(var b=["link.ace-main-stylesheet","link#main-ace-style",'link[href*="/ace.min.css"]','link[href*="/ace.css"]'],c=[],d=0;d<b.length&&(c=a("head").find(b[d]).first(),!(c.length>0));d++);g.find(".ajax-append-link").each(function(){var b=a(this);if(b.attr("href")){var d=jQuery("<link />",{type:"text/css",rel:"stylesheet","class":"ace-ajax-stylesheet"});c.length>0?d.insertBefore(c):d.appendTo("head"),d.attr("href",b.attr("href"))}b.remove()})},10),"function"==typeof h.settings.update_title?h.settings.update_title.call(null,c,b,l):h.settings.update_title===!0&&f(l),d||a("html,body").animate({scrollTop:0},250),g.trigger("ajaxloadcomplete",{url:b,hash:c}),h.stopLoading()}))}};var l=!1,m=null;this.startLoading=function(){j||(j=!0,this.settings.loading_overlay||"static"!=g.css("position")||(g.css("position","relative"),l=!0),k.remove(),k=a('<div class="ajax-loading-overlay"><i class="ajax-loading-icon '+(this.settings.loading_icon||"")+'"></i> '+this.settings.loading_text+"</div>"),"body"==this.settings.loading_overlay?a("body").append(k.addClass("ajax-overlay-body")):this.settings.loading_overlay?a(this.settings.loading_overlay).append(k):g.append(k),this.settings.max_load_wait!==!1&&(m=setTimeout(function(){if(m=null,j){var b;g.trigger(b=a.Event("ajaxloadlong")),b.isDefaultPrevented()||h.stopLoading(!0)}},1e3*this.settings.max_load_wait)))},this.stopLoading=function(a){a===!0?(j=!1,k.remove(),l&&(g.css("position",""),l=!1),null!=m&&(clearTimeout(m),m=null)):(k.addClass("almost-loaded"),g.one("ajaxscriptsloaded.inner_call",function(){h.stopLoading(!0)}))},this.working=function(){return j},this.loadScripts=function(b,c){a.ajaxPrefilter("script",function(a){a.cache=!0}),setTimeout(function(){function e(){"function"==typeof c&&c(),a('.btn-group[data-toggle="buttons"] > .btn').button(),g.trigger("ajaxscriptsloaded")}function f(a){a+=1,a<b.length?h(a):e()}function h(c){if(c=c||0,!b[c])return f(c);var g="js-"+b[c].replace(/[^\w\d\-]/g,"-").replace(/\-\-/g,"-");d[g]!==!0?a.getScript(b[c]).done(function(){d[g]=!0}).complete(function(){k++,k>=i&&j?e():f(c)}):f(c)}for(var i=0,k=0,l=0;l<b.length;l++)b[l]&&!function(){var a="js-"+b[l].replace(/[^\w\d\-]/g,"-").replace(/\-\-/g,"-");d[a]!==!0&&i++}();i>0?h():e()},10)},a(window).off("hashchange.ace_ajax").on("hashchange.ace_ajax",function(){var b=a.trim(window.location.hash);b&&0!=b.length&&h.loadUrl(b)}).trigger("hashchange.ace_ajax",[!0]);var n=a.trim(window.location.hash);!n&&this.settings.default_url&&(window.location.hash=this.settings.default_url)}var d={};a.fn.aceAjax=a.fn.ace_ajax=function(d,e,f,g){var h,i=this.each(function(){var i=a(this),j=i.data("ace_ajax"),k="object"==typeof d&&d;j||i.data("ace_ajax",j=new c(this,k)),"string"==typeof d&&"function"==typeof j[d]&&(h=g!=b?j[d](e,f,g):f!=b?j[d](e,f):j[d](e))});return h===b?i:h},a.fn.aceAjax.defaults=a.fn.ace_ajax.defaults={content_url:!1,default_url:!1,loading_icon:"fa fa-spin fa-spinner fa-2x orange",loading_text:"",loading_overlay:null,update_breadcrumbs:!0,update_title:!0,update_active:!0,close_active:!1,max_load_wait:!1}}(window.jQuery),function(a,b){if(ace.vars.touch){var c="touchstart MSPointerDown pointerdown",d="touchend touchcancel MSPointerUp MSPointerCancel pointerup pointercancel",e="touchmove MSPointerMove MSPointerHover pointermove";a.event.special.ace_drag={setup:function(){var f=0,g=a(this);g.on(c,function(c){function h(a){if(k){var b=a.originalEvent.touches?a.originalEvent.touches[0]:a;if(i={coords:[b.pageX,b.pageY]},k&&i&&(m=0,n=0,l=Math.abs(n=k.coords[1]-i.coords[1])>f&&Math.abs(m=k.coords[0]-i.coords[0])<=Math.abs(n)?n>0?"up":"down":Math.abs(m=k.coords[0]-i.coords[0])>f&&Math.abs(n)<=Math.abs(m)?m>0?"left":"right":!1,l!==!1)){var c={cancel:!1};k.origin.trigger({type:"ace_drag",direction:l,dx:m,dy:n,retval:c}),0==c.cancel&&a.preventDefault()}k.coords[0]=i.coords[0],k.coords[1]=i.coords[1]}}var i,j=c.originalEvent.touches?c.originalEvent.touches[0]:c,k={coords:[j.pageX,j.pageY],origin:a(c.target)},l=!1,m=0,n=0;g.on(e,h).one(d,function(){g.off(e,h),k=i=b})})}}}}(window.jQuery),function(a,b){function c(c,e){function f(){this.mobile_view=this.mobile_style<4&&this.is_mobile_view(),this.collapsible=!this.mobile_view&&this.is_collapsible(),this.minimized=!this.collapsible&&this.$sidebar.hasClass(l)||3==this.mobile_style&&this.mobile_view&&this.$sidebar.hasClass(m),this.horizontal=!(this.mobile_view||this.collapsible)&&this.$sidebar.hasClass(n)}var g=this;this.$sidebar=a(c),this.$sidebar.attr("data-sidebar","true"),this.$sidebar.attr("id")||this.$sidebar.attr("id","id-sidebar-"+ ++d);var h=ace.helper.getAttrSettings(c,a.fn.ace_sidebar.defaults,"sidebar-");this.settings=a.extend({},a.fn.ace_sidebar.defaults,e,h),this.minimized=!1,this.collapsible=!1,this.horizontal=!1,this.mobile_view=!1,this.vars=function(){return{minimized:this.minimized,collapsible:this.collapsible,horizontal:this.horizontal,mobile_view:this.mobile_view}},this.get=function(a){return this.hasOwnProperty(a)?this[a]:void 0},this.set=function(a,b){this.hasOwnProperty(a)&&(this[a]=b)},this.ref=function(){return this};var i=function(c){var d,e,f=a(this).find(ace.vars[".icon"]);f.length>0&&(d=f.attr("data-icon1"),e=f.attr("data-icon2"),c!==b?c?f.removeClass(d).addClass(e):f.removeClass(e).addClass(d):f.toggleClass(d).toggleClass(e))},j=function(){var b=g.$sidebar.find(".sidebar-collapse");return 0==b.length&&(b=a('.sidebar-collapse[data-target="#'+(g.$sidebar.attr("id")||"")+'"]')),b=0!=b.length?b[0]:null};this.toggleMenu=function(a,b){if(!this.collapsible){this.minimized=!this.minimized;try{ace.settings.sidebar_collapsed(c,this.minimized,!(a===!1||b===!1))}catch(d){this.minimized?this.$sidebar.addClass("menu-min"):this.$sidebar.removeClass("menu-min")}a||(a=j()),a&&i.call(a,this.minimized),ace.vars.old_ie&&ace.helper.redraw(c)}},this.collapse=function(a,b){this.collapsible||(this.minimized=!1,this.toggleMenu(a,b))},this.expand=function(a,b){this.collapsible||(this.minimized=!0,this.toggleMenu(a,b))},this.toggleResponsive=function(b){if(this.mobile_view&&3==this.mobile_style){if(this.$sidebar.hasClass("menu-min")){this.$sidebar.removeClass("menu-min");var c=j();c&&i.call(c)}if(this.minimized=!this.$sidebar.hasClass("responsive-min"),this.$sidebar.toggleClass("responsive-min responsive-max"),b||(b=this.$sidebar.find(".sidebar-expand"),0==b.length&&(b=a('.sidebar-expand[data-target="#'+(this.$sidebar.attr("id")||"")+'"]')),b=0!=b.length?b[0]:null),b){var d,e,f=a(b).find(ace.vars[".icon"]);f.length>0&&(d=f.attr("data-icon1"),e=f.attr("data-icon2"),f.toggleClass(d).toggleClass(e))}a(document).triggerHandler("settings.ace",["sidebar_collapsed",this.minimized])}},this.is_collapsible=function(){var b;return this.$sidebar.hasClass("navbar-collapse")&&null!=(b=a('.navbar-toggle[data-target="#'+(this.$sidebar.attr("id")||"")+'"]').get(0))&&b.scrollHeight>0},this.is_mobile_view=function(){var b;return null!=(b=a('.menu-toggler[data-target="#'+(this.$sidebar.attr("id")||"")+'"]').get(0))&&b.scrollHeight>0},this.$sidebar.on(ace.click_event+".ace.submenu",".nav-list",function(b){var c=this,d=a(b.target).closest("a");if(d&&0!=d.length){var e=g.minimized&&!g.collapsible;if(d.hasClass("dropdown-toggle")){b.preventDefault();var f=d.siblings(".submenu").get(0);if(!f)return!1;var h=a(f),i=0,j=f.parentNode.parentNode;if(e&&j==c||h.parent().hasClass("hover")&&"absolute"==h.css("position")&&!g.collapsible)return!1;var k=0==f.scrollHeight;return k&&a(j).find("> .open > .submenu").each(function(){this==f||a(this.parentNode).hasClass("active")||(i-=this.scrollHeight,g.hide(this,g.settings.duration,!1))}),k?(g.show(f,g.settings.duration),0!=i&&(i+=f.scrollHeight)):(g.hide(f,g.settings.duration),i-=f.scrollHeight),0!=i&&("true"!=g.$sidebar.attr("data-sidebar-scroll")||g.minimized||g.$sidebar.ace_sidebar_scroll("prehide",i)),!1}if("tap"==ace.click_event&&e&&d.get(0).parentNode.parentNode==c){var l=d.find(".menu-text").get(0);if(null!=l&&b.target!=l&&!a.contains(l,b.target))return b.preventDefault(),!1}if(ace.vars.ios_safari&&"false"!==d.attr("data-link"))return document.location=d.attr("href"),b.preventDefault(),!1}});var k=!1;this.show=function(b,c,d){if(d=d!==!1,d&&k)return!1;var e,f=a(b);if(f.trigger(e=a.Event("show.ace.submenu")),e.isDefaultPrevented())return!1;d&&(k=!0),c=c||this.settings.duration,f.css({height:0,overflow:"hidden",display:"block"}).removeClass("nav-hide").addClass("nav-show").parent().addClass("open"),b.scrollTop=0,c>0&&f.css({height:b.scrollHeight,"transition-property":"height","transition-duration":c/1e3+"s"});var g=function(b,c){b&&b.stopPropagation(),f.css({"transition-property":"","transition-duration":"",overflow:"",height:""}),c!==!1&&f.trigger(a.Event("shown.ace.submenu")),d&&(k=!1)};return c>0&&a.support.transition.end?f.one(a.support.transition.end,g):g(),ace.vars.android&&setTimeout(function(){g(null,!1),ace.helper.redraw(b)},c+20),!0},this.hide=function(b,c,d){if(d=d!==!1,d&&k)return!1;var e,f=a(b);if(f.trigger(e=a.Event("hide.ace.submenu")),e.isDefaultPrevented())return!1;d&&(k=!0),c=c||this.settings.duration,f.css({height:b.scrollHeight,overflow:"hidden",display:"block"}).parent().removeClass("open"),b.offsetHeight,c>0&&f.css({height:0,"transition-property":"height","transition-duration":c/1e3+"s"});var g=function(b,c){b&&b.stopPropagation(),f.css({display:"none",overflow:"",height:"","transition-property":"","transition-duration":""}).removeClass("nav-show").addClass("nav-hide"),c!==!1&&f.trigger(a.Event("hidden.ace.submenu")),d&&(k=!1)};return c>0&&a.support.transition.end?f.one(a.support.transition.end,g):g(),ace.vars.android&&setTimeout(function(){g(null,!1),ace.helper.redraw(b)},c+20),!0},this.toggle=function(a,b){if(b=b||g.settings.duration,0==a.scrollHeight){if(this.show(a,b))return 1}else if(this.hide(a,b))return-1;return 0};var l="menu-min",m="responsive-min",n="h-sidebar",o=function(){this.mobile_style=1,this.$sidebar.hasClass("responsive")&&!a('.menu-toggler[data-target="#'+this.$sidebar.attr("id")+'"]').hasClass("navbar-toggle")?this.mobile_style=2:this.$sidebar.hasClass(m)?this.mobile_style=3:this.$sidebar.hasClass("navbar-collapse")&&(this.mobile_style=4)};o.call(g),a(window).on("resize.sidebar.vars",function(){f.call(g)}).triggerHandler("resize.sidebar.vars")}var d=0;a(document).on(ace.click_event+".ace.menu",".menu-toggler",function(b){var c=a(this),d=a(c.attr("data-target"));if(0!=d.length){b.preventDefault(),d.toggleClass("display"),c.toggleClass("display");var e=ace.click_event+".ace.autohide",f="true"===d.attr("data-auto-hide");return c.hasClass("display")?(f&&a(document).on(e,function(b){return d.get(0)==b.target||a.contains(d.get(0),b.target)?void b.stopPropagation():(d.removeClass("display"),c.removeClass("display"),void a(document).off(e))}),"true"==d.attr("data-sidebar-scroll")&&d.ace_sidebar_scroll("reset")):f&&a(document).off(e),!1}}).on(ace.click_event+".ace.menu",".sidebar-collapse",function(b){var c=a(this).attr("data-target"),d=null;c&&(d=a(c)),(null==d||0==d.length)&&(d=a(this).closest(".sidebar")),0!=d.length&&(b.preventDefault(),d.ace_sidebar("toggleMenu",this))}).on(ace.click_event+".ace.menu",".sidebar-expand",function(b){var c=a(this).attr("data-target"),d=null;if(c&&(d=a(c)),(null==d||0==d.length)&&(d=a(this).closest(".sidebar")),0!=d.length){var e=this;b.preventDefault(),d.ace_sidebar("toggleResponsive",this);var f=ace.click_event+".ace.autohide";"true"===d.attr("data-auto-hide")&&(d.hasClass("responsive-max")?a(document).on(f,function(b){return d.get(0)==b.target||a.contains(d.get(0),b.target)?void b.stopPropagation():(d.ace_sidebar("toggleResponsive",e),void a(document).off(f))}):a(document).off(f))}}),a.fn.ace_sidebar=function(d,e){var f,g=this.each(function(){var b=a(this),g=b.data("ace_sidebar"),h="object"==typeof d&&d;g||b.data("ace_sidebar",g=new c(this,h)),"string"==typeof d&&"function"==typeof g[d]&&(f=e instanceof Array?g[d].apply(g,e):g[d](e))});return f===b?g:f},a.fn.ace_sidebar.defaults={duration:300}}(window.jQuery),function(a,b){function c(b,c){var f=this,g=a(window),h=a(b),i=h.find(".nav-list"),j=h.find(".sidebar-toggle").eq(0),k=h.find(".sidebar-shortcuts").eq(0),l=i.get(0);if(l){var m=ace.helper.getAttrSettings(b,a.fn.ace_sidebar_scroll.defaults);this.settings=a.extend({},a.fn.ace_sidebar_scroll.defaults,c,m);var n=f.settings.scroll_to_active,o=h.ace_sidebar("ref");h.attr("data-sidebar-scroll","true");var p=null,q=null,r=null,s=null,t=null,u=null;this.is_scrolling=!1;var v=!1;this.sidebar_fixed=e(b,"fixed");var w,x,y=function(){var a=i.parent().offset();return f.sidebar_fixed&&(a.top-=ace.helper.scrollTop()),g.innerHeight()-a.top-(f.settings.include_toggle?0:j.outerHeight())},z=function(){return l.clientHeight},A=function(b){if(!v&&f.sidebar_fixed){i.wrap('<div class="nav-wrap-up pos-rel" />'),i.after("<div><div></div></div>"),i.wrap('<div class="nav-wrap" />'),f.settings.include_toggle||j.css({"z-index":1}),f.settings.include_shortcuts||k.css({"z-index":99}),p=i.parent().next().ace_scroll({size:y(),mouseWheelLock:!0,hoverReset:!1,dragEvent:!0,styleClass:f.settings.scroll_style,touchDrag:!1}).closest(".ace-scroll").addClass("nav-scroll"),u=p.data("ace_scroll"),q=p.find(".scroll-content").eq(0),r=q.find(" > div").eq(0),t=a(u.get_track()),s=t.find(".scroll-bar").eq(0),f.settings.include_shortcuts&&0!=k.length&&(i.parent().prepend(k).wrapInner("<div />"),i=i.parent()),f.settings.include_toggle&&0!=j.length&&(i.append(j),i.closest(".nav-wrap").addClass("nav-wrap-t")),i.css({position:"relative"}),1==f.settings.scroll_outside&&p.addClass("scrollout"),l=i.get(0),l.style.top=0,q.on("scroll.nav",function(){l.style.top=-1*this.scrollTop+"px"}),i.on(a.event.special.mousewheel?"mousewheel.ace_scroll":"mousewheel.ace_scroll DOMMouseScroll.ace_scroll",function(a){return f.is_scrolling&&u.is_active()?p.trigger(a):!f.settings.lock_anyway}),i.on("mouseenter.ace_scroll",function(){t.addClass("scroll-hover")}).on("mouseleave.ace_scroll",function(){t.removeClass("scroll-hover")});var c=q.get(0);if(i.on("ace_drag.nav",function(b){if(!f.is_scrolling||!u.is_active())return void(b.retval.cancel=!0);if(0!=a(b.target).closest(".can-scroll").length)return void(b.retval.cancel=!0);if("up"==b.direction||"down"==b.direction){u.move_bar(!0);var d=b.dy;d=parseInt(Math.min(w,d)),Math.abs(d)>2&&(d=2*d),0!=d&&(c.scrollTop=c.scrollTop+d,l.style.top=-1*c.scrollTop+"px")}}),f.settings.smooth_scroll&&i.on("touchstart.nav MSPointerDown.nav pointerdown.nav",function(){i.css("transition-property","none"),s.css("transition-property","none")}).on("touchend.nav touchcancel.nav MSPointerUp.nav MSPointerCancel.nav pointerup.nav pointercancel.nav",function(){i.css("transition-property","top"),s.css("transition-property","top")}),d&&!f.settings.include_toggle){var e=j.get(0);e&&q.on("scroll.safari",function(){ace.helper.redraw(e)})}if(v=!0,1==b&&(f.reset(),n&&f.scroll_to_active(),n=!1),"number"==typeof f.settings.smooth_scroll&&f.settings.smooth_scroll>0&&(i.css({"transition-property":"top","transition-duration":(f.settings.smooth_scroll/1e3).toFixed(2)+"s"}),s.css({"transition-property":"top","transition-duration":(f.settings.smooth_scroll/1500).toFixed(2)+"s"}),p.on("drag.start",function(a){a.stopPropagation(),i.css("transition-property","none")}).on("drag.end",function(a){a.stopPropagation(),i.css("transition-property","top")})),ace.vars.android){var g=ace.helper.scrollTop();2>g&&(window.scrollTo(g,0),setTimeout(function(){f.reset()},20));var h,m=ace.helper.winHeight();a(window).on("scroll.ace_scroll",function(){f.is_scrolling&&u.is_active()&&(h=ace.helper.winHeight(),h!=m&&(m=h,f.reset()))})}}};this.scroll_to_active=function(){if(u&&u.is_active())try{var a,b=o.vars(),c=h.find(".nav-list");b.minimized&&!b.collapsible?a=c.find("> .active"):(a=i.find("> .active.hover"),0==a.length&&(a=i.find(".active:not(.open)")));var d=a.outerHeight();c=c.get(0);for(var e=a.get(0);e!=c;)d+=e.offsetTop,e=e.parentNode;var f=d-p.height();f>0&&(l.style.top=-f+"px",q.scrollTop(f))}catch(g){}},this.reset=function(a){if(a===!0&&(this.sidebar_fixed=e(b,"fixed")),!this.sidebar_fixed)return void this.disable();v||A();var c=o.vars(),d=!c.collapsible&&!c.horizontal&&(w=y())<(x=l.clientHeight);this.is_scrolling=!0,d&&(r.css({height:x,width:8}),p.prev().css({"max-height":w}),u.update({size:w}),u.enable(),u.reset()),d&&u.is_active()?h.addClass("sidebar-scroll"):this.is_scrolling&&this.disable()},this.disable=function(){this.is_scrolling=!1,p&&(p.css({height:"","max-height":""}),r.css({height:"",width:""}),p.prev().css({"max-height":""}),u.disable()),parseInt(l.style.top)<0&&f.settings.smooth_scroll&&a.support.transition.end?i.one(a.support.transition.end,function(){h.removeClass("sidebar-scroll"),i.off(".trans")}):h.removeClass("sidebar-scroll"),l.style.top=0},this.prehide=function(a){if(this.is_scrolling&&!o.get("minimized"))if(z()+a<y())this.disable();else if(0>a){var b=q.scrollTop()+a;if(0>b)return;l.style.top=-1*b+"px"}},this._reset=function(a){a===!0&&(this.sidebar_fixed=e(b,"fixed")),ace.vars.webkit?setTimeout(function(){f.reset()},0):this.reset()},this.set_hover=function(){t&&t.addClass("scroll-hover")},this.get=function(a){return this.hasOwnProperty(a)?this[a]:void 0},this.set=function(a,b){this.hasOwnProperty(a)&&(this[a]=b)},this.ref=function(){return this},this.updateStyle=function(a){null!=u&&u.update({styleClass:a})},h.on("hidden.ace.submenu.sidebar_scroll shown.ace.submenu.sidebar_scroll",".submenu",function(a){a.stopPropagation(),o.get("minimized")||(f._reset(),"shown"==a.type&&f.set_hover())}),A(!0)}}var d=ace.vars.safari&&navigator.userAgent.match(/version\/[1-5]/i),e="getComputedStyle"in window?function(a,b){return a.offsetHeight,window.getComputedStyle(a).position==b}:function(b,c){return b.offsetHeight,a(b).css("position")==c};a(document).on("settings.ace.sidebar_scroll",function(b,c){a(".sidebar[data-sidebar-scroll=true]").each(function(){var b=a(this),d=b.ace_sidebar_scroll("ref");if("sidebar_collapsed"==c&&e(this,"fixed"))"true"==b.attr("data-sidebar-hover")&&b.ace_sidebar_hover("reset"),d._reset();else if("sidebar_fixed"===c||"navbar_fixed"===c){var f=d.get("is_scrolling"),g=e(this,"fixed");d.set("sidebar_fixed",g),g&&!f?d._reset():g||d.disable()}})}),a(window).on("resize.ace.sidebar_scroll",function(){a(".sidebar[data-sidebar-scroll=true]").each(function(){var b=a(this);"true"==b.attr("data-sidebar-hover")&&b.ace_sidebar_hover("reset");var c=a(this).ace_sidebar_scroll("ref"),d=e(this,"fixed");c.set("sidebar_fixed",d),c._reset()})}),a.fn.ace_sidebar_scroll||(a.fn.ace_sidebar_scroll=function(d,e){var f,g=this.each(function(){var b=a(this),g=b.data("ace_sidebar_scroll"),h="object"==typeof d&&d;g||b.data("ace_sidebar_scroll",g=new c(this,h)),"string"==typeof d&&"function"==typeof g[d]&&(f=g[d](e))});return f===b?g:f},a.fn.ace_sidebar_scroll.defaults={scroll_to_active:!0,include_shortcuts:!0,include_toggle:!1,smooth_scroll:150,scroll_outside:!1,scroll_style:"",lock_anyway:!1})}(window.jQuery),function(a,b){function c(b,c){function h(b){var c=b,d=a(c),e=null,f=!1;this.show=function(){null!=e&&clearTimeout(e),e=null,d.addClass("hover-show hover-shown"),f=!0;for(var a=0;a<g.length;a++)g[a].find(".hover-show").not(".hover-shown").each(function(){i(this).hide()})},this.hide=function(){f=!1,d.removeClass("hover-show hover-shown hover-flip"),null!=e&&clearTimeout(e),e=null;var a=d.find("> .submenu").get(0);a&&j(a,"hide")},this.hideDelay=function(a){null!=e&&clearTimeout(e),d.removeClass("hover-shown"),e=setTimeout(function(){f=!1,d.removeClass("hover-show hover-flip"),e=null;var b=d.find("> .submenu").get(0);b&&j(b,"hide"),"function"==typeof a&&a.call(this)},m.settings.sub_hover_delay)},this.is_visible=function(){return f}}function i(b){var c=a(b).data("subHide");return c||a(b).data("subHide",c=new h(b)),c}function j(b,c){var d=a(b).data("ace_scroll");return d?"string"==typeof c?(d[c](),!0):d:!1}function k(c){var d=a(this),f=a(c);c.style.top="",c.style.bottom="";var g=null;q.minimized&&(g=d.find(".menu-text").get(0))&&(g.style.marginTop="");var h=ace.helper.scrollTop(),i=0,k=h;v&&(i=b.offsetTop,k+=i+1);var m=d.offset();m.top=parseInt(m.top);var n,o=0;c.style.maxHeight="";var r=c.scrollHeight,n=d.height();g&&(o=n,m.top+=o);var u=parseInt(m.top+r),x=0,y=t.height(),z=parseInt(m.top-k-o),A=y,B=q.horizontal,C=!1;B&&this.parentNode==p&&(x=0,m.top+=d.height(),C=!0),!C&&(x=u-(y+h))>=0&&(x=z>x?x:z,0==x&&(x=20),z-x>10&&(x+=parseInt(Math.min(25,z-x))),m.top+(n-o)>u-x&&(x-=m.top+(n-o)-(u-x)),x>0&&(c.style.top=-x+"px",g&&(g.style.marginTop=-x+"px"))),0>x&&(x=0);
var D=x>0&&x>n-20;if(D?d.addClass("pull_up"):d.removeClass("pull_up"),B)if(d.parent().parent().hasClass("hover-flip"))d.addClass("hover-flip");else{var E=f.offset(),F=f.width(),G=t.width();E.left+F>G&&d.addClass("hover-flip")}var H=d.hasClass("hover")&&!q.mobile_view;if(!(H&&f.find("> li > .submenu").length>0)){var I=A-(m.top-h)+x,J=x-I;if(J>0&&n>J&&(I+=parseInt(Math.max(n,n-J))),I-=5,!(90>I)){var K=!1;if(e)f.addClass("sub-scroll").css("max-height",I+"px");else{if(K=j(c),0==K){f.ace_scroll({observeContent:!0,detached:!0,updatePos:!1,reset:!0,mouseWheelLock:!0,styleClass:l.settings.sub_scroll_style}),K=j(c);var L=K.get_track();L&&f.after(L)}K.update({size:I})}if(w=I,!e&&K){I>14&&r-I>4?(K.enable(),K.reset()):K.disable();var L=K.get_track();if(L){L.style.top=-(x-o-1)+"px";var m=f.position(),M=m.left;M+=s?2:f.outerWidth()-K.track_size(),L.style.left=parseInt(M)+"px",C&&(L.style.left=parseInt(M-2)+"px",L.style.top=parseInt(m.top)+(g?o-2:0)+"px")}}ace.vars.safari&&ace.helper.redraw(c)}}}var l=this,m=this,n=ace.helper.getAttrSettings(b,a.fn.ace_sidebar_hover.defaults);this.settings=a.extend({},a.fn.ace_sidebar_hover.defaults,c,n);var o=a(b),p=o.find(".nav-list").get(0);o.attr("data-sidebar-hover","true"),g.push(o);var q={},r=ace.vars.old_ie,s=!1;d&&(l.settings.sub_hover_delay=parseInt(Math.max(l.settings.sub_hover_delay,2500)));var t=a(window),u=a(".navbar").eq(0),v="fixed"==u.css("position");this.update_vars=function(){v="fixed"==u.css("position")},l.dirty=!1,this.reset=function(){0!=l.dirty&&(l.dirty=!1,o.find(".submenu").each(function(){var b=a(this),c=b.parent();b.css({top:"",bottom:"","max-height":""}),b.hasClass("ace-scroll")?b.ace_scroll("disable"):b.removeClass("sub-scroll"),f(this,"absolute")?b.addClass("can-scroll"):b.removeClass("can-scroll"),c.removeClass("pull_up").find(".menu-text:first").css("margin-top","")}),o.find(".hover-show").removeClass("hover-show hover-shown hover-flip"))},this.updateStyle=function(a){sub_scroll_style=a,o.find(".submenu.ace-scroll").ace_scroll("update",{styleClass:a})},this.changeDir=function(a){s="right"===a};var w=-1;e||o.on("hide.ace.submenu.sidebar_hover",".submenu",function(b){if(!(1>w)){b.stopPropagation();var c=a(this).closest(".ace-scroll.can-scroll");0!=c.length&&f(c[0],"absolute")&&c[0].scrollHeight-this.scrollHeight<w&&c.ace_scroll("disable")}}),e||o.on("shown.ace.submenu.sidebar_hover hidden.ace.submenu.sidebar_hover",".submenu",function(){if(!(1>w)){var b=a(this).closest(".ace-scroll.can-scroll");if(0!=b.length&&f(b[0],"absolute")){var c=b[0].scrollHeight;w>14&&c-w>4?b.ace_scroll("enable").ace_scroll("reset"):b.ace_scroll("disable")}}});var x=-1,y=d?"touchstart.sub_hover":"mouseenter.sub_hover",z=d?"touchend.sub_hover touchcancel.sub_hover":"mouseleave.sub_hover";o.on(y,".nav-list li, .sidebar-shortcuts",function(){if(q=o.ace_sidebar("vars"),!q.collapsible){var b=a(this),c=!1,e=b.hasClass("hover"),g=b.find("> .submenu").get(0);if(!(g||this.parentNode==p||e||(c=b.hasClass("sidebar-shortcuts"))))return void(g&&a(g).removeClass("can-scroll"));var h=g,j=!1;if(h||this.parentNode!=p||(h=b.find("> a > .menu-text").get(0)),!h&&c&&(h=b.find(".sidebar-shortcuts-large").get(0)),!(h&&(j=f(h,"absolute"))||e))return void(g&&a(g).removeClass("can-scroll"));var m=i(this);if(g)if(j){l.dirty=!0;var n=ace.helper.scrollTop();if(!m.is_visible()||!d&&n!=x||r)if(a(g).addClass("can-scroll"),r||d){var s=this;setTimeout(function(){k.call(s,g)},0)}else k.call(this,g);x=n}else a(g).removeClass("can-scroll");m.show()}}).on(z,".nav-list li, .sidebar-shortcuts",function(){q=o.ace_sidebar("vars"),q.collapsible||a(this).hasClass("hover-show")&&i(this).hideDelay()})}if(!ace.vars.very_old_ie){var d=ace.vars.touch,e=ace.vars.old_ie||d,f="getComputedStyle"in window?function(a,b){return a.offsetHeight,window.getComputedStyle(a).position==b}:function(b,c){return b.offsetHeight,a(b).css("position")==c};a(window).on("resize.sidebar.ace_hover",function(){a(".sidebar[data-sidebar-hover=true]").ace_sidebar_hover("update_vars").ace_sidebar_hover("reset")}),a(document).on("settings.ace.ace_hover",function(b,c){"sidebar_collapsed"==c?a(".sidebar[data-sidebar-hover=true]").ace_sidebar_hover("reset"):"navbar_fixed"==c&&a(".sidebar[data-sidebar-hover=true]").ace_sidebar_hover("update_vars")});var g=[];a.fn.ace_sidebar_hover=function(d,e){var f,g=this.each(function(){var b=a(this),g=b.data("ace_sidebar_hover"),h="object"==typeof d&&d;g||b.data("ace_sidebar_hover",g=new c(this,h)),"string"==typeof d&&"function"==typeof g[d]&&(f=g[d](e))});return f===b?g:f},a.fn.ace_sidebar_hover.defaults={sub_sub_hover_delay:750,sub_scroll_style:"no-track scroll-thin"}}}(window.jQuery),function(a,b){function c(b,c){var d=b.find(".widget-main").eq(0);a(window).off("resize.widget.scroll");var e=ace.vars.old_ie||ace.vars.touch;if(c){var f=d.data("ace_scroll");f&&d.data("save_scroll",{size:f.size,lock:f.lock,lock_anyway:f.lock_anyway});var g=b.height()-b.find(".widget-header").height()-10;g=parseInt(g),d.css("min-height",g),e?(f&&d.ace_scroll("disable"),d.css("max-height",g).addClass("overflow-scroll")):(f?d.ace_scroll("update",{size:g,mouseWheelLock:!0,lockAnyway:!0}):d.ace_scroll({size:g,mouseWheelLock:!0,lockAnyway:!0}),d.ace_scroll("enable").ace_scroll("reset")),a(window).on("resize.widget.scroll",function(){var a=b.height()-b.find(".widget-header").height()-10;a=parseInt(a),d.css("min-height",a),e?d.css("max-height",a).addClass("overflow-scroll"):d.ace_scroll("update",{size:a}).ace_scroll("reset")})}else{d.css("min-height","");var h=d.data("save_scroll");h&&d.ace_scroll("update",{size:h.size,mouseWheelLock:h.lock,lockAnyway:h.lock_anyway}).ace_scroll("enable").ace_scroll("reset"),e?d.css("max-height","").removeClass("overflow-scroll"):h||d.ace_scroll("disable")}}var d=function(b){this.$box=a(b);this.reload=function(){var a=this.$box,b=!1;"static"==a.css("position")&&(b=!0,a.addClass("position-relative")),a.append('<div class="widget-box-overlay"><i class="'+ace.vars.icon+'loading-icon fa fa-spinner fa-spin fa-2x white"></i></div>'),a.one("reloaded.ace.widget",function(){a.find(".widget-box-overlay").remove(),b&&a.removeClass("position-relative")})},this.close=function(){var a=this.$box,b=300;a.fadeOut(b,function(){a.trigger("closed.ace.widget"),a.remove()})},this.toggle=function(a,b){var c=this.$box,d=c.find(".widget-body").eq(0),e=null,f="undefined"!=typeof a?a:c.hasClass("collapsed")?"show":"hide",g="show"==f?"shown":"hidden";if("undefined"==typeof b&&(b=c.find("> .widget-header a[data-action=collapse]").eq(0),0==b.length&&(b=null)),b){e=b.find(ace.vars[".icon"]).eq(0);var h,i=null,j=null;(i=e.attr("data-icon-show"))?j=e.attr("data-icon-hide"):(h=e.attr("class").match(/fa\-(.*)\-(up|down)/))&&(i="fa-"+h[1]+"-down",j="fa-"+h[1]+"-up")}var k=250,l=200;"show"==f?(e&&e.removeClass(i).addClass(j),d.hide(),c.removeClass("collapsed"),d.slideDown(k,function(){c.trigger(g+".ace.widget")})):(e&&e.removeClass(j).addClass(i),d.slideUp(l,function(){c.addClass("collapsed"),c.trigger(g+".ace.widget")}))},this.hide=function(){this.toggle("hide")},this.show=function(){this.toggle("show")},this.fullscreen=function(){var a=this.$box.find("> .widget-header a[data-action=fullscreen]").find(ace.vars[".icon"]).eq(0),b=null,d=null;(b=a.attr("data-icon1"))?d=a.attr("data-icon2"):(b="fa-expand",d="fa-compress"),this.$box.hasClass("fullscreen")?(a.addClass(b).removeClass(d),this.$box.removeClass("fullscreen"),c(this.$box,!1)):(a.removeClass(b).addClass(d),this.$box.addClass("fullscreen"),c(this.$box,!0)),this.$box.trigger("fullscreened.ace.widget")}};a.fn.widget_box=function(c,e){var f,g=this.each(function(){var b=a(this),g=b.data("widget_box"),h="object"==typeof c&&c;g||b.data("widget_box",g=new d(this,h)),"string"==typeof c&&(f=g[c](e))});return f===b?g:f},a(document).on("click.ace.widget",".widget-header a[data-action]",function(b){b.preventDefault();var c=a(this),e=c.closest(".widget-box");if(0!=e.length&&!e.hasClass("ui-sortable-helper")){var f=e.data("widget_box");f||e.data("widget_box",f=new d(e.get(0)));var g=c.data("action");if("collapse"==g){var h,i=e.hasClass("collapsed")?"show":"hide";if(e.trigger(h=a.Event(i+".ace.widget")),h.isDefaultPrevented())return;f.toggle(i,c)}else if("close"==g){var h;if(e.trigger(h=a.Event("close.ace.widget")),h.isDefaultPrevented())return;f.close()}else if("reload"==g){c.blur();var h;if(e.trigger(h=a.Event("reload.ace.widget")),h.isDefaultPrevented())return;f.reload()}else if("fullscreen"==g){var h;if(e.trigger(h=a.Event("fullscreen.ace.widget")),h.isDefaultPrevented())return;f.fullscreen()}else"settings"==g&&e.trigger("setting.ace.widget")}})}(window.jQuery),function(a){a("#ace-settings-btn").on(ace.click_event,function(b){b.preventDefault(),a(this).toggleClass("open"),a("#ace-settings-box").toggleClass("open")}),a("#ace-settings-navbar").on("click",function(){ace.settings.navbar_fixed(null,this.checked)}).each(function(){this.checked=ace.settings.is("navbar","fixed")}),a("#ace-settings-sidebar").on("click",function(){ace.settings.sidebar_fixed(null,this.checked)}).each(function(){this.checked=ace.settings.is("sidebar","fixed")}),a("#ace-settings-breadcrumbs").on("click",function(){ace.settings.breadcrumbs_fixed(null,this.checked)}).each(function(){this.checked=ace.settings.is("breadcrumbs","fixed")}),a("#ace-settings-add-container").on("click",function(){ace.settings.main_container_fixed(null,this.checked)}).each(function(){this.checked=ace.settings.is("main-container","fixed")}),a("#ace-settings-compact").on("click",function(){if(this.checked){a("#sidebar").addClass("compact");var b=a("#ace-settings-hover");b.length>0&&b.removeAttr("checked").trigger("click")}else a("#sidebar").removeClass("compact"),a("#sidebar[data-sidebar-scroll=true]").ace_sidebar_scroll("reset");ace.vars.old_ie&&ace.helper.redraw(a("#sidebar")[0],!0)}),a("#ace-settings-highlight").on("click",function(){this.checked?a("#sidebar .nav-list > li").addClass("highlight"):a("#sidebar .nav-list > li").removeClass("highlight"),ace.vars.old_ie&&ace.helper.redraw(a("#sidebar")[0])}),a("#ace-settings-hover").on("click",function(){if(!a("#sidebar").hasClass("h-sidebar")){if(this.checked)a("#sidebar li").addClass("hover").filter(".open").removeClass("open").find("> .submenu").css("display","none");else{a("#sidebar li.hover").removeClass("hover");var b=a("#ace-settings-compact");b.length>0&&b.get(0).checked&&b.trigger("click")}a(".sidebar[data-sidebar-hover=true]").ace_sidebar_hover("reset"),a(".sidebar[data-sidebar-scroll=true]").ace_sidebar_scroll("reset"),ace.vars.old_ie&&ace.helper.redraw(a("#sidebar")[0])}})}(jQuery),function(a){a("#ace-settings-rtl").removeAttr("checked").on("click",function(){b()});var b=function(){function b(b){function c(a,b){e.find("."+a).removeClass(a).addClass("tmp-rtl-"+a).end().find("."+b).removeClass(b).addClass(a).end().find(".tmp-rtl-"+a).removeClass("tmp-rtl-"+a).addClass(b)}var d=a(document.body);b||d.toggleClass("rtl"),b=b||document.body;var e=a(b);e.find(".dropdown-menu:not(.datepicker-dropdown,.colorpicker)").toggleClass("dropdown-menu-right").end().find(".pull-right:not(.dropdown-menu,blockquote,.profile-skills .pull-right)").removeClass("pull-right").addClass("tmp-rtl-pull-right").end().find(".pull-left:not(.dropdown-submenu,.profile-skills .pull-left)").removeClass("pull-left").addClass("pull-right").end().find(".tmp-rtl-pull-right").removeClass("tmp-rtl-pull-right").addClass("pull-left").end().find(".chosen-select").toggleClass("chosen-rtl").next().toggleClass("chosen-rtl"),c("align-left","align-right"),c("no-padding-left","no-padding-right"),c("arrowed","arrowed-right"),c("arrowed-in","arrowed-in-right"),c("tabs-left","tabs-right"),c("messagebar-item-left","messagebar-item-right"),a(".modal.aside-vc").ace_aside("flip").ace_aside("insideContainer"),e.find(".fa").each(function(){if(!(this.className.match(/ui-icon/)||a(this).closest(".fc-button").length>0))for(var b=this.attributes.length,c=0;b>c;c++){var d=this.attributes[c].value;d.match(/fa\-(?:[\w\-]+)\-left/)?this.attributes[c].value=d.replace(/fa\-([\w\-]+)\-(left)/i,"fa-$1-right"):d.match(/fa\-(?:[\w\-]+)\-right/)&&(this.attributes[c].value=d.replace(/fa\-([\w\-]+)\-(right)/i,"fa-$1-left"))}});var f=d.hasClass("rtl");f?(e.find(".scroll-hz").addClass("make-ltr").find(".scroll-content").wrapInner('<div class="make-rtl" />'),a(".sidebar[data-sidebar-hover=true]").ace_sidebar_hover("changeDir","right")):(e.find(".scroll-hz").removeClass("make-ltr").find(".make-rtl").children().unwrap(),a(".sidebar[data-sidebar-hover=true]").ace_sidebar_hover("changeDir","left")),a.fn.ace_scroll&&e.find(".scroll-hz").ace_scroll("reset");try{var g=a("#piechart-placeholder");if(g.length>0){var h=d.hasClass("rtl")?"nw":"ne";g.data("draw").call(g.get(0),g,g.data("chart"),h)}}catch(i){}ace.helper.redraw(b,!0)}if(0==a("#ace-rtl-stylesheet").length){var c=a("head").find("link.ace-main-stylesheet");0==c.length&&(c=a("head").find('link[href*="/ace.min.css"],link[href*="/ace-part2.min.css"]'),0==c.length&&(c=a("head").find('link[href*="/ace.css"],link[href*="/ace-part2.css"]')));var d=a("head").find("link#ace-skins-stylesheet"),e=c.first().attr("href").replace(/(\.min)?\.css$/i,"-rtl$1.css");a.ajax({url:e}).done(function(){var a=jQuery("<link />",{type:"text/css",rel:"stylesheet",id:"ace-rtl-stylesheet"});d.length>0?a.insertAfter(d):c.length>0?a.insertAfter(c.last()):a.appendTo("head"),a.attr("href",e),b(),window.Pace&&Pace.running&&Pace.stop()})}else b();a(".page-content-area[data-ajax-content=true]").on("ajaxscriptsloaded.rtl",function(){a("body").hasClass("rtl")&&b(this)})}}(jQuery),function(a){try{a("#skin-colorpicker").ace_colorpicker({auto_pos:!1})}catch(b){}a("#skin-colorpicker").on("change",function(){function b(b){var c=a(document.body);c.removeClass("no-skin skin-1 skin-2 skin-3"),c.addClass(b),ace.data.set("skin",b);var d=["red","blue","green",""];a(".ace-nav > li.grey").removeClass("dark"),a(".ace-nav > li").removeClass("no-border margin-1"),a(".ace-nav > li:not(:last-child)").removeClass("light-pink").find("> a > "+ace.vars[".icon"]).removeClass("pink").end().eq(0).find(".badge").removeClass("badge-warning"),a(".sidebar-shortcuts .btn").removeClass("btn-pink btn-white").find(ace.vars[".icon"]).removeClass("white"),a(".ace-nav > li.grey").removeClass("red").find(".badge").removeClass("badge-yellow"),a(".sidebar-shortcuts .btn").removeClass("btn-primary btn-white");var e=0;a(".sidebar-shortcuts .btn").each(function(){a(this).find(ace.vars[".icon"]).removeClass(d[e++])});var f=["btn-success","btn-info","btn-warning","btn-danger"];if("no-skin"==b){var e=0;a(".sidebar-shortcuts .btn").each(function(){a(this).attr("class","btn "+f[e++%4])}),a(".sidebar[data-sidebar-scroll=true]").ace_sidebar_scroll("updateStyle",""),a(".sidebar[data-sidebar-hover=true]").ace_sidebar_hover("updateStyle","no-track scroll-thin")}else if("skin-1"==b){a(".ace-nav > li.grey").addClass("dark");var e=0;a(".sidebar-shortcuts").find(".btn").each(function(){a(this).attr("class","btn "+f[e++%4])}),a(".sidebar[data-sidebar-scroll=true]").ace_sidebar_scroll("updateStyle","scroll-white no-track"),a(".sidebar[data-sidebar-hover=true]").ace_sidebar_hover("updateStyle","no-track scroll-thin scroll-white")}else if("skin-2"==b)a(".ace-nav > li").addClass("no-border margin-1"),a(".ace-nav > li:not(:last-child)").addClass("light-pink").find("> a > "+ace.vars[".icon"]).addClass("pink").end().eq(0).find(".badge").addClass("badge-warning"),a(".sidebar-shortcuts .btn").attr("class","btn btn-white btn-pink").find(ace.vars[".icon"]).addClass("white"),a(".sidebar[data-sidebar-scroll=true]").ace_sidebar_scroll("updateStyle","scroll-white no-track"),a(".sidebar[data-sidebar-hover=true]").ace_sidebar_hover("updateStyle","no-track scroll-thin scroll-white");else if("skin-3"==b){c.addClass("no-skin"),a(".ace-nav > li.grey").addClass("red").find(".badge").addClass("badge-yellow");var e=0;a(".sidebar-shortcuts .btn").each(function(){a(this).attr("class","btn btn-primary btn-white"),a(this).find(ace.vars[".icon"]).addClass(d[e++])}),a(".sidebar[data-sidebar-scroll=true]").ace_sidebar_scroll("updateStyle","scroll-dark no-track"),a(".sidebar[data-sidebar-hover=true]").ace_sidebar_hover("updateStyle","no-track scroll-thin")}a(".sidebar[data-sidebar-scroll=true]").ace_sidebar_scroll("reset"),ace.vars.old_ie&&ace.helper.redraw(document.body,!0)}var c=a(this).find("option:selected").data("skin");if(0==a("#ace-skins-stylesheet").length){var d=a("head").find("link.ace-main-stylesheet");0==d.length&&(d=a("head").find('link[href*="/ace.min.css"],link[href*="/ace-part2.min.css"]'),0==d.length&&(d=a("head").find('link[href*="/ace.css"],link[href*="/ace-part2.css"]')));var e=d.first().attr("href").replace(/(\.min)?\.css$/i,"-skins$1.css");a.ajax({url:e}).done(function(){var a=jQuery("<link />",{type:"text/css",rel:"stylesheet",id:"ace-skins-stylesheet"});d.length>0?a.insertAfter(d.last()):a.appendTo("head"),a.attr("href",e),b(c),window.Pace&&Pace.running&&Pace.stop()})}else b(c)})}(jQuery),function(a){a(document).on("reload.ace.widget",".widget-box",function(){var b=a(this);setTimeout(function(){b.trigger("reloaded.ace.widget")},parseInt(1e3*Math.random()+1e3))})}(window.jQuery),function(a){ace.vars.US_STATES=["Alabama","Alaska","Arizona","Arkansas","California","Colorado","Connecticut","Delaware","Florida","Georgia","Hawaii","Idaho","Illinois","Indiana","Iowa","Kansas","Kentucky","Louisiana","Maine","Maryland","Massachusetts","Michigan","Minnesota","Mississippi","Missouri","Montana","Nebraska","Nevada","New Hampshire","New Jersey","New Mexico","New York","North Dakota","North Carolina","Ohio","Oklahoma","Oregon","Pennsylvania","Rhode Island","South Carolina","South Dakota","Tennessee","Texas","Utah","Vermont","Virginia","Washington","West Virginia","Wisconsin","Wyoming"];try{a("#nav-search-input").bs_typeahead({source:ace.vars.US_STATES,updater:function(b){return a("#nav-search-input").focus(),b}})}catch(b){}}(window.jQuery);